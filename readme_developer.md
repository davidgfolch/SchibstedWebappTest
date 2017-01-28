
# Schibsted web-app test


## How to run

See readme.md

## Git
git add README.md<br/>
git commit -a -m "first commit"<br/>
git remote add origin https://github.com/davidgfolch/SchibstedWebappTest.git<br/>
git push -u origin master<br/>

### Generating ssh key for ssh connection
[Source documentation](https://help.github.com/articles/connecting-to-github-with-ssh/)<br/>
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"<br/>
sudo apt-get install xclip<br/>
xclip -sel clip < ~/.ssh/id_rsa.pub<br/>
Copy your key and add it in github.com -> settings -> SSH and GPG keys<br/>
git remote -v<br/>
NOOOO!! git remote set-url origin ssh://davidgfolch@github.com/davidgfolch/SchibstedWebappTest.git<br/>
YEEES!! git remote set-url origin ssh://git@github.com/davidgfolch/SchibstedWebappTest.git<br/>

## Sources
http://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
http://stackoverflow.com/questions/13155734/eclipse-cant-recognize-com-sun-net-httpserver-httpserver-package
http://maven.apache.org/plugins/maven-assembly-plugin/usage.html
