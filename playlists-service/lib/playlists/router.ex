defmodule Playlists.Router do
  @playlists "user_playlists"

  # Bring Plug.Router module into scope
  use Plug.Router
  alias Playlists.JSONUtils, as: JSON

  plug CORSPlug, origin: ["http://localhost:3000"]


  # Attach the Logger to log incoming requests
  plug(Plug.Logger)

  # Tell Plug to match the incoming request with the defined endpoints
  plug(:match)

  plug(Plug.Parsers,
    parsers: [:json],
    pass: ["application/json"],
    json_decoder: Jason
  )

  plug(:dispatch)

  get "/playlist" do
    # Find all the posts in the database
    posts =
      Mongo.find(:mongo, @playlists, %{})
      # For each of the post normalise the id
      |> Enum.map(&JSON.normaliseMongoId/1)
      # Convert the records to a list
      |> Enum.to_list()
      # Encode the list to a JSON string
      |> Jason.encode!()
    conn
    |> put_resp_content_type("application/json")
    # Send a 200 OK response with the posts in the body
    |> send_resp(200, posts)
  end

  post "/playlist" do
    case conn.body_params do
      %{"user_id" => user_id, "playlist_name" => playlist_name} = json ->
        existingDoc =
          Mongo.find_one(:mongo, "user_playlists", %{
            "$and" => [%{user_id: user_id}, %{playlist_name: playlist_name}]
          })

        case existingDoc do
          nil ->
            case Mongo.insert_one(:mongo, @playlists, json) do
              {:ok, playlist} ->
                doc = Mongo.find_one(:mongo, @playlists, %{_id: playlist.inserted_id})
                post =
                  JSON.normaliseMongoId(doc)
                  |> Jason.encode!()
                conn
                |> put_resp_content_type("application/json")
                |> send_resp(200, post)
              {:error, _} ->
                send_resp(conn, 500, "Something went wrong")
            end
          _ ->
            post =
              JSON.normaliseMongoId(existingDoc)
              |> Jason.encode!()
            conn
            |> put_resp_content_type("application/json")
            |> send_resp(200, post)
        end
      _ ->
        send_resp(conn, 400, '')
    end
  end

  get "/playlist/:id" do
    doc = Mongo.find_one(:mongo, @playlists, %{_id: BSON.ObjectId.decode!(id)})
    case doc do
      nil ->
        send_resp(conn, 404, "Not Found")
      %{} ->
        post =
          JSON.normaliseMongoId(doc)
          |> Jason.encode!()
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, post)
      {:error, _} ->
        send_resp(conn, 500, "Something went wrong")
    end
  end

  put "playlist/song" do
    case conn.body_params do
      %{"playlist_id" => playlist_id, "song_id" => song_id} ->
        case Mongo.find_one_and_update(
               :mongo,
               @playlists,
               %{_id: BSON.ObjectId.decode!(playlist_id)},
               %{
                 "$addToSet" => %{songs: song_id}
               },
               return_document: :after
             ) do
          {:ok, doc} ->
            case doc do
              nil ->
                send_resp(conn, 404, "Not Found")
              _ ->
                post =
                  JSON.normaliseMongoId(doc)
                  |> Jason.encode!()
                conn
                |> put_resp_content_type("application/json")
                |> send_resp(200, post)
            end
          {:error, _} ->
            send_resp(conn, 500, "Something went wrong")
        end
      _ ->
        send_resp(conn, 400, '')
    end
  end

  delete "playlist/song" do
    case conn.body_params do
      %{"playlist_id" => playlist_id, "song_id" => song_id} ->
        case Mongo.find_one_and_update(
               :mongo,
               @playlists,
               %{_id: BSON.ObjectId.decode!(playlist_id)},
               %{
                 "$pull" => %{songs: song_id}
               },
               return_document: :after
             ) do
          {:ok, doc} ->
            case doc do
              nil ->
                send_resp(conn, 404, "Not Found")
              _ ->
                post =
                  JSON.normaliseMongoId(doc)
                  |> Jason.encode!()
                conn
                |> put_resp_content_type("application/json")
                |> send_resp(200, post)
            end
          {:error, _} ->
            send_resp(conn, 500, "Something went wrong")
        end
      _ ->
        send_resp(conn, 400, '')
    end
  end

  delete "playlist/:id" do
    Mongo.delete_one!(:mongo, @playlists, %{_id: BSON.ObjectId.decode!(id)})
    conn
    |> put_resp_content_type("application/json")
    |> send_resp(200, Jason.encode!(%{id: id}))
  end

  # Fallback handler when there was no match
  match _ do
    send_resp(conn, 404, "Not Found")
  end
end
