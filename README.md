<div style="text-align: center;">

[![AGPL-3.0 License][license-shield]][license-url]
[![2009scape Fork][fork-shield]][fork-url]
[![Kotlin version][kotlin-version]][kotlin-url]
[![Java version][java-version]][java-url]
[![Sp-release][sp-release]][sp-url]
[![Wiki][wiki-shield]][wiki-url]

</div>

## 2009Scape

Fork for educational purposes & single-player experience. 



##### Setting Up the Project

***

##### GitLab Setup

1. Create a GitLab account if you haven't done so already.

2. Follow the Git Basics guide [over on the wiki.](https://gitlab.com/rs-source/2009scape/-/wikis/home)

**If at anytime you have an issue with GitLab please refer to the [GitLab help center](https://gitlab.com/help).**

***

##### Prerequisites

These are mandatory. If you don't install **all** of these programs **in order** prior to
the project's setup, things won't work. At all.

*For Windows users* - Turn developer mode on first in Windows developer settings.

* [JDK 11](https://adoptium.net) or the Java SE Development Kit Version 11
* [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/)

***

##### SSH setup

1. [Set up a key if you don't have one (ed25519)](https://docs.gitlab.com/ee/user/ssh.html#generate-an-ssh-key-pair)
2. [Add your public key to your gitlab account](https://docs.gitlab.com/ee/user/ssh.html#add-an-ssh-key-to-your-gitlab-account)
3. [Verify you can connect to git@gitlab.com](https://docs.gitlab.com/ee/user/ssh.html#verify-that-you-can-connect)

***

##### Project Setup

1. If you haven't already, make sure to follow our [Git Basics](https://gitlab.com/2009scape/2009scape/-/wikis/git-basics) guide.
2. Run `git lfs pull` in the 2009scape folder you cloned as part of that guide. This only has to be done once, ever.
3. Follow our [IntelliJ IDEA Setup Guide](https://gitlab.com/2009scape/2009scape/-/wikis/Setup-for-IntelliJ-IDEA-IDE)

***

##### Running the project

###### Linux / OSX

Start the game server with the included run script. Use `./run -h` for more info.

###### Windows

Start the game server with `run-server.bat`

***

##### License

AGPL 3.0 license, which can be found [here](https://www.gnu.org/licenses/agpl-3.0.en.html).

[license-shield]: https://img.shields.io/badge/license-AGPL--3.0-informational
[license-url]: https://www.gnu.org/licenses/agpl-3.0.en.html

[fork-shield]: https://img.shields.io/badge/repository-fork-blue
[fork-url]: https://gitlab.com/2009scape/2009scape

[sp-release]: https://img.shields.io/badge/singleplayer-release-blue
[sp-url]: https://github.com/szumaster3/2009scape-game

[kotlin-version]: https://img.shields.io/badge/kotlin-1.8.20-blue.svg?logo=kotlin
[kotlin-url]: http://kotlinlang.org

[java-version]: https://img.shields.io/badge/java-11-blue.svg?logo=openjdk
[java-url]: https://adoptium.net/temurin/releases/?version=11

[wiki-shield]: https://img.shields.io/badge/wiki-guides-blue.svg?logo=wikipedia
[wiki-url]: https://gitlab.com/rs-source/2009scape/-/wikis/home
