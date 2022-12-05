# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.

# General application configuration
use Mix.Config

config :playlists_service,
port: 4000,
database: "spotify-dev",
pool_size: 10,
collection: "user_playlists"

import_config "#{Mix.env()}.exs"
