defmodule Favorites.Router do
  @user_favorites "user_favorites"
  @song_favorites "song_favorites"

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

   get "/user/:id" do
        doc = Mongo.find_one(:mongo, @user_favorites, %{_id: id})
        case doc do
          nil ->
            send_resp(conn, 404, "Not Found")
          _ ->
            send_resp(conn, 200, doc |> Jason.encode!())
        end
  end

  get "/song/:id" do
        doc = Mongo.find_one(:mongo, @song_favorites, %{_id: id})
        case doc do
          nil ->
            send_resp(conn, 404, "Not Found")
          _ ->
            send_resp(conn, 200, doc |> Jason.encode!())
        end
  end


  post "/song/favorite" do
    case conn.body_params do
      %{"user_id" => user_id, "song_id" => song_id} = json ->
          Mongo.update_one(:mongo,
            @user_favorites,
            %{_id: user_id},
            %{
              "$addToSet" => %{favorites: song_id}
            },
            upsert: true);
            Mongo.update_one(:mongo,
            @song_favorites,
            %{_id: song_id},
            %{
              "$addToSet" => %{favorites: user_id}
            },
            upsert: true);
           conn
            |> put_resp_content_type("application/json")
            |> send_resp(200, "")
      _ ->
        send_resp(conn, 400, '')
    end
  end

  delete "/song/favorite" do
    case conn.body_params do
      %{"user_id" => user_id, "song_id" => song_id} ->
          Mongo.update_one(:mongo,
            @user_favorites,
            %{_id: user_id},
            %{
              "$pull" => %{favorites: song_id}
            },
            upsert: true);

            Mongo.update_one(:mongo,
            @song_favorites,
            %{_id: song_id},
            %{
              "$pull" => %{favorites: user_id}
            },
            upsert: true);


           conn
            |> put_resp_content_type("application/json")
            |> send_resp(200, "")
      _ ->
        send_resp(conn, 400, '')
    end
  end

  match _ do
    send_resp(conn, 404, "Not Found")
  end

end
