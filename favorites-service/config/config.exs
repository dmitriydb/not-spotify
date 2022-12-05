# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.

# General application configuration
use Mix.Config

config :favorites_service,
port: 4001,
database: "spotify-dev",
pool_size: 10

import_config "#{Mix.env()}.exs"
