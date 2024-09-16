## Project setup

***

**_Make sure you have followed all the steps in [gitlab setup page guide](https://gitlab.com/rs-source/game/-/wikis/main/%7BSource-setup%7D) before starting this thread._**

### Fork the repository

***

1. Go to the page of the project you want to fork.
2. In the upper right corner you will find the **fork** button, click it.<br>
   ![4](https://i.imgur.com/Nahtart.png)
3. Select the project url = your gitlab username, and click fork project.<br>
   ![5](https://i.imgur.com/h28Hph6.png)
5. Once the process is complete, you should be redirected to your account exactly to the homepage of the project you forked.
6. To get started work with the project, It needs to be moved to intelij idea, on your fork page, click the code button and copy the url.  
   ![6](https://i.imgur.com/AsUaLN3.png)

7. Start the IntelliJ IDEA and click **_Get from VCS_** button.<br>
   ![1](https://i.imgur.com/8A5VBQJ.png)
8. To clone the fork into the program we will need [GIT](https://gitforwindows.org/index.html), you can download it right now by clicking **_download and install_**.<br>
   ![2](https://i.imgur.com/Beto8tJ.png)
9. When the process finishes paste the previously copied **_URL_** of your fork, and wait for it to done.<br>

10. If you get this message after paste the url, go back to [gitlab setup page guide](https://gitlab.com/rs-source/game/-/wikis/main/%7BSource-setup%7D) and setup ssh again.<br>
    ![7](https://i.imgur.com/pKYNbZN.png)

11. When it finishes the process, the project will automatically open, when it is before that the program requires you to get permission select yes if you want to continue.
12. Once the project has opened in the lower right corner you will see a window indicating that you can **_Load maven_** config, do so and the project will automatically load, next.

### Running the project

***

###### Linux / OSX

Start the game server with the included run script. Use `./run -h` for more info.

###### Windows

1. Start the game server with `run-server.bat`<br>
2. You can also use maven plugins, on the right side of the program:<br>
   ![17](https://i.imgur.com/guCIxqP.png)

### Contributions

***

1. If you want to send your work to the main repository, add official repository as your upstream
2. In program click the terminal on bottom left corner and paste:
   `git remote add upstream https://gitlab.com/2009scape/2009scape`<br>
   ![9](https://i.imgur.com/ic0irvY.png)

### Merge

1. When you push changes to your repository, on the gitlab page, there will be an option for you to send this commit to the main repository.
2. Click New merge request option, select target branch master, continue, fill in the description (of the changes you made) and approve.<br>
   ![10](https://i.imgur.com/9A4q5jK.png)

If you have encountered a problem with GitLab please refer to the help center available at this [link]( https://gitlab.com/help).