#!/usr/bin/env zsh

source _load_properties.sh

output_directory_html="${githubdirectory_location_local}${github_html_repo}";

cd ${output_directory_html}

# Install with brew install wget

## wget
# --recursive : go through the entire website
# --level : the depth of recursive. default is 5, but inf = infinite, so it should do all pages
# --no-host-directories : output pages directly into the directory rather than a 'localhost/8084' folder
# --timestamping : don't download multiples of the same file. (might now work) (the following 2 flags are used with it)
# --html-extension : write html files as html files
# --reject : do not download any images
# --domains : restrict download to localhost only
# -e robots=off : ignores robots.txt
# http://localhost:8084/ <-- this is the website (this website) to fetch

start_timestamp=$(date +"%Y-%m-%d %H:%M:%S")

wget \
  --recursive \
  --level=inf \
  --no-host-directories \
  --timestamping \
    --no-if-modified-since \
    --no-use-server-timestamps \
  --html-extension \
  --reject jpg,jpeg,png,gif,mpg,webp \
  --domains localhost \
  -e robots=off \
  http://localhost:8084


wget \
  --no-host-directories \
  http://localhost:8084/manifest \
  http://localhost:8084/sitemap \
  http://localhost:8084/llms.txt

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

mv "feed" "feed.xml"
mv "sitemap" "sitemap.xml"
mv "manifest" "manifest.json"

mkdir "blog"
cp "feed.xml" "blog/index.xml"

cp "errorpage.html" "404.html"
cp "index.html" "index2.html"

end_timestamp=$(date +"%Y-%m-%d %H:%M:%S")

echo "Update Complete -- Start time: $start_timestamp -- End time: $end_timestamp"

exit 0
