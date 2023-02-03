#!/usr/bin/env zsh

source _load_properties.sh

cd ${generation_output_directory}

# Install with brew install wget

## wget
# --recursive : go through the entire website
# --no-host-directories : output pages directly into the directory rather than a 'localhost/8084' folder
# --html-extension : write html files as html files
# --reject : do not download any images
## http://localhost:8084/ <-- this is the website (this website) to fetch

wget \
  --recursive \
  --no-host-directories \
  --html-extension \
  --reject jpg,jpeg,png,gif \
  http://localhost:8084/

echo "update complete"

exit 0
