# IRPersonalNews
Information Retrieval Personal News Search System



In the Web 2.0 era one of the most important and relevant aspects is to try to organize, filter and select news and information in a way that is more relevant to our interests, our purpose and our goal.

This result can be achieved thanks to recommendation engines that allow to select, filter and propose to the user only a series of relevant contents and information in order to be able to unravel more effectively in the digital jungle of constant content.

This aspect of dynamism and speed of information is particularly relevant in the world of social networks.

This project therefore proposes an automatic system that allows the user to filter messages on twitter, based on several factors:  words written by the user, some reference topics and articles previously read by the user himself, in order to provide different levels of customization of the information and contents

The project is part of the Information Retrieval master degree exam at Universit`a degli Studi Milano-Bicocca. 



The project architecture is the following:

![ProjectArchitecture](./report/Resources/OverallArchitecture.png)



### Project Organization

The project is organized in 3 section:

- **Tweet downloader** (Python app in the folder: PythonApps)
- **News downloader** (Python app in the folder: PythonApps)
- **IRPersonalNews webapp** (Java Spring web application with maven)

In the folder there are also the **slides**, the Luke tool used for index debug, some .sh script for task automation and the **pdf report** of the project.



### Launch and test the project

If you want to launch the project you have to go to the folder:

> irpersonalnews > target 

And launch the following command in shell or cmd:

> java -jar irpersonalnews-1.0.war

Then open the browser to: http://localhost:8080 and you can use the web application.



**Considerations**

The project has been tested and implemented on unix platforms (linux and macosx)

The python projects are self sustainable, so you have to launch the main.py inside the Articles or Tweets folder.

Please remember that if you want to test the python apps you have to install the required libraries.