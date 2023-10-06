# TheSchlock.com

This repository is the code base for my personal website, <a href="theschlock.com">theschlock.com</a>.

Written in Java, it runs an old version of Tapestry with a MySql database. Use gradle to fetch all of the dependencies and build the IntelliJ project file. After getting everything to build, create a `deploy.properties` file from the sample, and fill in all the details. The site is run locally with `com.schlock.website.AppRunner`.

I removed a lot of the dynamic components of the site that tapestry offers, so it can be rendered statically and uploaded easily. To render the website, run `generate_website.sh`.

For me, generated files are put into <a href="https://github.com/dyreschlock/dyreschlock.github.io">dyreschlock.github.io</a>. When pushed, the site will redeploy. Images are currently hosted on <a href="https://github.com/dyreschlock/dyreschlock.github.photos">dyreschlock.github.photos</a>, but this can be easily changed.

There's a hidden page at `/regeneration` which can only be accessed locally. It is used to generate all of the images for the site, and some other things.