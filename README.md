I Can Win:
1. Install git and generate a pair of ssh keys. Authorize the public key on github.com.
2. Specify your user.name and user.email in git.
3. Create a new repository on github.com and clone it locally to your computer.
4. Create a file called song.txt and put there half the text of your favorite song.
5. Make a commit called "add first half of my favorite song" and send it to the server.
6. Make sure github has a song.txt file with the lyrics.
7. Using the github's web interface, add the second half of the lyrics and make a commit with the name "finish my song".
8. Make a pull in the local repository and make sure that the commit you created on github is pulled up and you have all the lyrics.

Bring It On:
This task is performed immediately after the previous one (I Can Win).
1. Add a .gitignore file to the project and configure it to hide files with the extension .db, .log and directories with the names target or bin.
2. Create a feature branch and add two commits to it
3. Merge the feature branch in master
4. Return to feature and create the arrows.txt file with the following contents:
The ship glides gently on the waves
As day turns into night
Make a commit.
5. Go to master. Create the arrows.txt file there and add the following text:
One thousand burning arrows
Fill the starlit sky
Make a commit.
6. Merge feature in master resolving the conflict: save all 4 lines in arrows.txt file in the order they were added in steps 4 and 5.
