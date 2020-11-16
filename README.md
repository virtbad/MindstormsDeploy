# MindstormsDeploy
> This Program can combine your codebase distributed on different Files into one file to build and copy on your EV3.
## Features 
* Code Collection
* Code Sorting to Priorities
* Copying to Clipboard
### Pending
* Deploying to Website
* Copying onto EV3
## Usage
1. Download Executable from Release Section
2. Copy Executable into your Project folder
3. Start Jar using the Terminal (Enter ``` java -jar MindstormsDeploy.jar ```)
4. Customize your *deployconfig.json* (Only on first run)
5. Start Jar again (Only on first run)
6. Press Enter to build (Copies code into your Clipboard automatically)
7. Paste Code from your Clipboard into the Makecode Editor
8. Download and copy built file onto your EV3
### Configuration
If you first run MindstormsDeploy, it will create a file which will look like this.
You can change those values to fit your needs.
``` javascript 
  "infinityMode": false,
  "code": {
    "codeFileEndings": [
      ".ts"
    ],
    "sourceFolder": "src",
    "priorityPrefix": "//priority:"
  }
```
#### infinityMode
If true, you can press enter after a build to start the next one. Default: false
#### codeFileEndings
File endings which should be treated as source files. Default: .ts
#### sourceFolder
Folder which contains the source code. Default: src
#### priorityPrefix
Trimmed, to Lowercase and Spaceremoved prefix of the priority decleration. Default: //priority:
### Priorities
To allow for more control about which file is where in the built version, there is the concept of Priorities. To prioritize a file, you can add the priority prefix with the priority into the top Line.
<br>By default the priority prefix is ```//priority:```. To Specify a prioirty you could now type ``` // Priority: [number]```.
<br>The lower the Number, the further down the file will be put.
## Version
The current version is **1.2.1**
## Lisence
This Project is licenced under the MIT Lisence. Have a look at the LICENSE File for more Information.
