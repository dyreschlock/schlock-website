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
  --reject jpg,jpeg,png,gif,mpg \
  http://localhost:8084/


# find = finds all directories excluding .dot directories
for dir in $(find . -mindepth 1 -type d -not \( -path "./.*" -prune \)) ; do

    filename="${dir}.html"

    #if an HTML file has the same name as a directory in the same directory
    if test -f "$filename"; then

        #move that file into that directory and rename it to index.html
        indexname="${dir}/index.html"

        mv "$filename" "${indexname}"

        echo "Moved ${filename} to ${indexname}"
    fi
done

echo "update complete"

exit 0
