defmodule Favorites.Application do
  # See https://hexdocs.pm/elixir/Application.html
  # for more information on OTP Applications
  @moduledoc false

  use Application

  @impl true
  def start(_type, _args) do
    children = [
      {Plug.Cowboy, scheme: :http, plug: Favorites.Router, options: [port: Application.get_env(:favorites_service, :port)]},
      {
        Mongo,
        [
          name: :mongo,
          database: Application.get_env(:favorites_service, :database),
          pool_size: Application.get_env(:favorites_service, :pool_size)
        ]
      }
    ]

    # See https://hexdocs.pm/elixir/Supervisor.html
    # for other strategies and supported options
    opts = [strategy: :one_for_one, name: Playlists.Supervisor]
    Supervisor.start_link(children, opts)
  end
end
