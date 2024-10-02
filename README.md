### 2009scape

[![License][license-shield]][license-url] [![Fork][fork-shield]][fork-url] [![Kotlin][kotlin-version]][kotlin-url] [![Java][java-version]][java-url] [![Release][play-release]][play-url]

Fork of official repository for educational purposes & single-player experience but mostly for personal use.

***

<details>

<summary>Setting up project</summary>

***

### Setting up project

*For Windows users* - Turn developer mode on first in Windows developer settings.

1. Create a GitLab account.
2. Install [JDK 11](https://adoptium.net)
3. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

</details>

<details>

<summary>SSH setup</summary>

1. [Set up a key if you don't have one (ed25519)](https://docs.gitlab.com/ee/user/ssh.html#generate-an-ssh-key-pair)
2. [Add your public key to your gitlab account](https://docs.gitlab.com/ee/user/ssh.html#add-an-ssh-key-to-your-gitlab-account)
3. [Verify you can connect to git@gitlab.com](https://docs.gitlab.com/ee/user/ssh.html#verify-that-you-can-connect)

</details>

<details>

<summary>Install Git</summary>
<details>
<summary>on Windows</summary>

***

### Git on Windows

![Windows](https://i.imgur.com/p2UtTDY.png)

#### 1. Download

Visit the [official Git website](https://git-scm.com/download/win) to download the latest version of the Git installer
for Windows. The download should start automatically when you visit the page.

#### 2. Run the Installer

Launch the downloaded installer and follow the installation wizard.

#### 3. Verify the install via *Git Bash*

To ensure that Git has been installed correctly, open Git Bash and type the following command:

```  
git --version  
```

Press **Enter**, and the name of the version of Git you just installed should appear.

</details>
<details>
<summary>on macOS</summary>

***

### Git on macOS

#### 1. Download

Visit the [official Git website](https://git-scm.com/download/mac) to download the latest version of the Git installer
for macOS.

![macOs](https://i.imgur.com/vCe9oRG.png)

#### 2. Complete Installation Instructions

Once the installer is downloaded, open the .dmg file and follow the installation instructions.

#### 3. Verify Installation With Terminal

To ensure that Git has been installed correctly, open Terminal and type the following command:

```  
git --version  
```  

Press Enter, and you should see the version of Git you installed displayed on the next line.

</details>
<details>
<summary>on Linux</summary>  

***

### Git on Linux

#### 1. Install Via Package Manager

![Linux](https://i.imgur.com/htuf2ii.png)

The easiest way to install Git on Linux is through the package manager for your distribution. For Debian-based
distributions like Ubuntu, you can use the apt package manager:

```  
sudo apt-get install git  
```  

#### 2. Verify the Installation

***

Open Terminal and type in the following:

```  
git --version  
```  

</details>
</details>
<details>  

<summary>Fork the repository</summary>

***

#### Fork the repository

1. Go to the page of the project you want to fork.
2. In the upper right corner you will find the **fork** button, click it.  
   ![Fork](https://i.imgur.com/Nahtart.png)

3. Select the project url = your gitlab username, and click fork project.  
   ![Url](https://i.imgur.com/h28Hph6.png)

4. Once the process is complete, you should be redirected to your account exactly to the homepage of the project you
   forked.
5. To get started work with the project, It needs to be moved to intelij idea, on your fork page, click the code button
   and copy the url.    
   ![Copy](https://i.imgur.com/AsUaLN3.png)

6. Start the IntelliJ IDEA and click **_Get from VCS_** button.  
   ![Vcs](https://i.imgur.com/8A5VBQJ.png)

7. To clone the fork into the program we will need [GIT](https://gitforwindows.org/index.html) (If you have not used the
   instructions above continue), you can download it right now by clicking **_download and install_**.  
   ![Install](https://i.imgur.com/Beto8tJ.png)

8. When the process finishes paste the previously copied **_URL_** of your fork, and wait for it to done.<br>
9. If you get this message after paste the url:, setup ssh again  
   ![Error](https://i.imgur.com/pKYNbZN.png)

10. When it finishes the process, the project will automatically open, when it is before that the program requires you
    to get permission select yes if you want to continue.
11. Once the project has opened in the lower right corner you will see a window indicating that you can **_Load maven_**
    config, do so and the project will automatically load, next.

***

</details>

<details>  

<summary>Running the project</summary>   

***

### Running the project

#### Linux / OSX

Start the game server with the included run script. Use `./run -h` for more info.

#### Windows

Start the game server with `run-server.bat`  
You can also use maven plugins, on the right side of the program:  
![17](https://i.imgur.com/guCIxqP.png)

***

</details>
<details>  

<summary>Contributions</summary>  

***

### Contributions

1. If you want to send your work to the main repository, add official repository as your upstream
2. In program click the terminal on bottom left corner and paste:

```  
git remote add upstream https://gitlab.com/2009scape/2009scape  
 ``` 

![9](https://i.imgur.com/ic0irvY.png)

***

</details>
<details>  

<summary>Merge</summary>   

***

### Merge

1. When you push changes to your repository, on the gitlab page, there will be an option for you to send this commit to
   the main repository.
2. Click New merge request option, select target branch master, continue, fill in the description (of the changes you
   made) and approve.  
   ![10](https://i.imgur.com/9A4q5jK.png)

If you have encountered a problem with GitLab please refer to the help center available at
this [link]( https://gitlab.com/help).

***

</details>

<details>  

<summary>

~~Server setup~~

</summary>  

***

### Server Setup

* ~~Set up `db` user with read/write permissions on server database~~
* ~~Put creds in `credentials.json`~~
* ~~`sudo apt install libmariadb3 libmariadb-dev`~~

***

</details>

<details>  

<summary>Singleplayer setup</summary> 

***

### Singleplayer

To run singleplayer, read the following information.

### Setup

1. Download [GitHub Desktop](https://desktop.github.com/download/) app.  
![](https://i.imgur.com/RZnyFVo.png)
2. Go to [singleplayer](https://github.com/szumaster1/game) repository.
3. Fork this repository to your repositories, then Clone it using github desktop app.  
![](https://i.imgur.com/GM2vT7k.png)  
![](https://github.com/user-attachments/assets/96765cd1-e5a4-47f3-8a3b-2b40b1f9a656)  
![](https://github.com/user-attachments/assets/83b6b35f-35d5-4cc3-a6a5-9c2ebcaa72a8)

4. Run `launch.bat` on Windows, or `launch.sh` on a UN*X system.  
![](https://i.imgur.com/y7lQ5F7.png)

5. If the server starts, run `client.jar`.

***

</details>
<details>

<summary>Single-player file structure</summary>

***


```
singleplayer_folder
├─ .gitignore
├─ launch.bat
├─ launch.sh
│
├─ game
│  │ ├─ client.jar
│  │ ├─ config.json
│  │ └─ server.jar
│  │
│  ├─ data
│  │   │ └─ ObjectParser.xml
│  │   │
│  │   ├─ botdata
│  │   │    ├─ botnames.txt
│  │   │    ├─ bot_dialogue.json
│  │   │    ├─ getnames.sh
│  │   │    ├─ ge_bot_appearances_and_equipment.json
│  │   │    ├─ namesandarmor.txt
│  │   │    ├─ namesandarmorscript
│  │   │    └─ pestcontrolcopies.txt
│  │   ├─ cache
│  │   │    ├─ main_file_cache.dat2
│  │   │    ├─ main_file_cache.idx0
│  │   │    ├─ main_file_cache.idx1
│  │   │    ├─ main_file_cache.idx2
│  │   │    ├─ main_file_cache.idx3
│  │   │    ├─ main_file_cache.idx4
│  │   │    ├─ main_file_cache.idx5
│  │   │    ├─ main_file_cache.idx6
│  │   │    ├─ main_file_cache.idx7
│  │   │    ├─ main_file_cache.idx8
│  │   │    ├─ main_file_cache.idx9
│  │   │    ├─ main_file_cache.idx10
│  │   │    ├─ main_file_cache.idx11
│  │   │    ├─ main_file_cache.idx12
│  │   │    ├─ main_file_cache.idx13
│  │   │    ├─ main_file_cache.idx14
│  │   │    ├─ main_file_cache.idx15
│  │   │    ├─ main_file_cache.idx16
│  │   │    ├─ main_file_cache.idx17
│  │   │    ├─ main_file_cache.idx18
│  │   │    ├─ main_file_cache.idx19
│  │   │    ├─ main_file_cache.idx20
│  │   │    ├─ main_file_cache.idx21
│  │   │    ├─ main_file_cache.idx22
│  │   │    ├─ main_file_cache.idx23
│  │   │    ├─ main_file_cache.idx24
│  │   │    ├─ main_file_cache.idx25
│  │   │    ├─ main_file_cache.idx26
│  │   │    ├─ main_file_cache.idx27
│  │   │    ├─ main_file_cache.idx28
│  │   │    └─ main_file_cache.idx255
│  │   │
│  │   ├─ configs
│  │   │    ├─ account_limit_exceptions.conf
│  │   │    ├─ ammo_configs.json
│  │   │    ├─ clue_rewards.json
│  │   │    ├─ door_configs.json
│  │   │    ├─ drop_tables.json
│  │   │    ├─ ground_spawns.json
│  │   │    ├─ interface_configs.json
│  │   │    ├─ item_configs.json
│  │   │    ├─ music_configs.json  
│  │   │    ├─ npc_configs.json
│  │   │    ├─ npc_spawns.json
│  │   │    ├─ object_configs.json
│  │   │    ├─ ranged_weapon_configs.json
│  │   │    ├─ shops.json
│  │   │    ├─ varbit_definitions.json
│  │   │    ├─ xteas.json
│  │   │    │
│  │   │    └─ shared_tables
│  │   │         ├─ ASDT.xml
│  │   │         ├─ CELEDT.xml
│  │   │         ├─ GDT.xml
│  │   │         ├─ HDT.xml
│  │   │         ├─ RDT.xml
│  │   │         ├─ RSDT.xml
│  │   │         └─ USDT.xml
│  │   │
│  │   ├─ eco
│  │   │   └─ .gitignore
│  │   │
│  │   └─ players
│  │       └─ ...
│  │
│  │
│  └─ worldprops
│          └─ default.conf
│
└─ jre
 └─ ...
 ```

</details>
<details>

<summary>Default server configuration</summary>

***


```  
[server]
#Log Level - the level of verbosity used for logs.
#"verbose" - ALL logs are shown.
#"detailed" - FINE logs are hidden, which is generally bulk/debug info.
#"cautious" - FINE, INFO logs are hidden, meaning this level only shows warnings and errors.
#"silent" - FINE, INFO, WARN logs are hidden, meaning this level only shows errors.
log_level = "verbose"
#Secret key - this is sent by the client during login.
#Client/Server MUST match or connection is refused.
secret_key = "2009scape_development"
write_logs = true
msip = "127.0.0.1" # 192.168.1.3
#preload the map (Increases memory usage by 2GB but makes game ticks smoother).
preload_map = false
#--------Note: If both of the below are false, no database is required to run the server.--------------
#true = login requires password to be correct, passwords are hashed before stored. false = login does not care about the correctness of a password.
use_auth = false #NOTE: THIS MUST BE SET TO TRUE IN PRODUCTION!
#true - account data (credits, playtime, etc) is persisted, false - account data is purely temporary.
#NOTE: this does not affect actual save data, like stats, inventory, etc.
persist_accounts = false #NOTE: THIS MUST BE SET TO TRUE IN PRODUCTION!
noauth_default_admin = true #NOTE: If we are not using auth, this determines whether or not players are admins by default.
#------------------------------------------------------------------------------------------------------
#The limit on how many different accounts a player can log into per day.
daily_accounts_per_ip = 5
watchdog_enabled = false
#smartpathfinder_bfs = true

[database]
database_name = "global"
database_username = "root"
database_password = ""
database_address = "127.0.0.1"
database_port = "3306"

[integrations]
grafana_logging = false
grafana_log_path = "@data/logs"
grafana_log_ttl_days = 3

[world]
#Server name.
name = "RuneScape"
#name used for announcements of bots selling items on the GE.
name_ge = "2009scape"
#Toggle debug mode.
debug = true
#Toggle development mode.
dev = true
#Toggle gui (old).
start_gui = false
#Toggle daily restart (reset server store every 24h).
daily_restart = false
#World number.
world_id = "1"
#Country id.
country_id = "0"
#Toggle if the world should be member or not.
members = true
#activity as displayed on the world list.
activity = "2009scape classic."
#Toggle PVP mode.
pvp = false
#Default XP rate.
default_xp_rate = 1.0
#Enables a default clan for players to join automatically. Should be an account with the same name as @name, with a clan set up already.
enable_default_clan = false
#Enable or disable AI bots.
enable_bots = true
#Message of the week model ID, 0 for random.
motw_identifier = "0"
#Text shown for message of the week - @name will be replaced with the name property set above.
motw_text = "Welcome to @name emulation!"
#The coordinates new players spawn at.
new_player_location = "3094,3107,0"
#The location of home teleport.
home_location = "3222,3218,0"
#Auto stock.
autostock_ge = true
#Buy tokens for coins (For FOG, RC Store).
allow_token_purchase = true
#Enable special custom perks system.
skillcape_perks = true
#Increase door time (Old).
increased_door_time = true
#Enable use of "scripts".
enable_botting = false
#Number of bots in-game.
max_adv_bots = 100
#Toggle bot that double money in Grand Exchange.
enable_doubling_money_scammers = true
#Enable Wilderness PVP.
wild_pvp_enabled = false
#Toggle Jad practice (for tokkul, safe fight).
jad_practice_enabled = false
#minimum HA value for announcements of bots selling on ge.
ge_announcement_limit = 500
#Toggle castle wars (Not yet).
enable_castle_wars = false
#Enable personalized shops (back-port)
personalized_shops = false
#Enable override prices by bots on Grand Exchange.
bots_influence_ge_price = true
#verbose cutscene logging (for cutscenes in the new system).
verbose_cutscene = false
#show the rules the first time a player logs in.
show_rules = false
#the number of revenants active at a time.
revenant_population = 30
#enable auto-buy/auto-sell on the GE.
i_want_to_cheat = false
#better agility pyramid gp reward (gp reward = 1000 + ((agility level / 99) * 9000)).
better_agility_pyramid_gp = true
#better dragonfire shield attack (30 second cooldown instead of 2 minutes).
better_dfs = true
#new player announcement.
new_player_announcement = true
#enables holiday random events (no effect on normal random events).
holiday_event_randoms = true
#force holiday randoms (can only force one at a time).
force_halloween_randoms = false
force_christmas_randoms = false
force_easter_randoms = true
#runecrafting formula revision (573 introduced probabilistic multiple runes, 581 extrapolated probabilistic runes past 99).
runecrafting_formula_revision = 581
#one click for opening bank interface.
bank_booth_quick_open = true

[paths]
#path to the data folder, which contains the cache subfolder and such.
data_path = "data"
#in the lines below, @data will be replaced with the value set for data_path.
cache_path = "@data/cache"
store_path = "@data/serverstore"
save_path = "@data/players"
configs_path = "@data/configs"
#this is where economy/grand exchange data gets saved.
grand_exchange_data_path = "@data/eco"
#path to file defining the rare drop table.
rare_drop_table_path = "@data/configs/shared_tables/RDT.xml"
#path to file defining c.ele minor drop table.
cele_drop_table_path = "@data/configs/shared_tables/CELEDT.xml"
#path to file defining the uncommon seed drop table.
uncommon_seed_drop_table_path = "@data/configs/shared_tables/USDT.xml"
#path to file defining the herb drop table.
herb_drop_table_path = "@data/configs/shared_tables/HDT.xml"
#path to file defining the gem drop table.
gem_drop_table_path = "@data/configs/shared_tables/GDT.xml"
#path to file defining the rare seed drop table.
rare_seed_drop_table_path = "@data/configs/shared_tables/RSDT.xml"
#path to file defining the allotment seed drop table.
allotment_seed_drop_table_path = "@data/configs/shared_tables/ASDT.xml"
#path to file containing boot-time object changes.
object_parser_path = "@data/ObjectParser.xml"
#path logs are written to.
logs_path = "@data/logs"
bot_data = "@data/botdata"
eco_data = "@data/eco"

```

</details>
<details><summary>

Commands (only for admin)

</summary>

***

#### Command list


````  
::1hit  
::addcredits  
::addxp  
::allmusic  
::allowaggro  
::allquest  
::anim  
::announce  
::appearance  
::audio  
::balloon  
::ban  
::bank  
::barrage  
::botinfo bot_name  
::bury  
::calc_accuracy  
::calc_accuracy npc_id  
::calcmaxhit  
::cancelupdate  
::charge  
::cleardiary  
::clearjob  
::commands  
::completediaries  
::coords  
::cs2  
::csvmodcr  
::datamap  
::debug  
::define_varbit  
::drawchunks  
::drawclipping  
::drawintersect  
::drawregions  
::drawroute  
::dumpappearance  
::dumpdatamaps  
::dumpstructs  
::empty  
::emptybank  
::expression  
::f  
::farmkit  
::findobj  
::finishbins  
::finishtask  
::fmanim  
::fmend  
::fmspeed  
::fmspeedend  
::fmstart  
::ge bot  
::ge buying  
::ge search  
::ge selling  
::geprivacy  
::getattribute  
::getnpcparent  
::getobjectvarp  
::getvarbit  
::giveitem  
::globalaudio  
::god  
::grow  
::home  
::iface  
::iftriggers  
::infinitespecial  
::interface  
::invis  
::ipban  
::item  
::itemsearch  
::jail  
::kick  
::killme  
::listifmodels  
::listiftext  
::loc  
::log  
::loopanim  
::makeover  
::mapredo  
::max  
::modcr  
::model  
::movcam  
::mrboneswildride  
::mute  
::muteglobal  
::noobme  
::npc  
::npcanim  
::npcsearch  
::npcsearch name  
::object  
::objectgrid  
::overlay  
::players  
::playid  
::playjingle id  
::playsong id  
::pnpc  
::poscam  
::potato  
::quest  
::quests  
::ranim  
::region  
::reloadjson  
::removeitem  
::removeitemall  
::reply  
::resetanim  
::resetbins  
::resetcam  
::resetpassword  
::rolldrops  
::rolltrawlerloot  
::rotcam  
::rules  
::runekit  
::sconfigrange  
::sconfigrange0  
::script  
::scripts  
::setattribute  
::setlevel  
::setpasswordother  
::setplaqueread  
::setqueststage  
::setslayerpoints  
::setslayertask  
::setvarbit  
::setvarc  
::setvarp  
::shakecam  
::shop  
::skip  
::spellbook  
::stats  
::stopscript  
::struct  
::tele  
::teleobj  
::teleto  
::teletome  
::testfm  
::testpacket  
::timers  
::to  
::update  
::varbits  
::xface
````  

</details>

<details>
<summary>FAQ</summary>

***

### FAQ

#### Add admin rights:

You'll need to edit default config and set `noauth_default_admin = false` to `true`

####  How to change xp rates:

You'll need to edit default config `default xp rate` to `default xp rate = desired rate`

</details>

***

### License

AGPL 3.0 license, The license applies to the entire repository, unless otherwise specified. Full license can be
found [here](https://www.gnu.org/licenses/agpl-3.0.en.html).

[license-shield]: https://img.shields.io/badge/license-AGPL--3.0-informational

[license-url]: https://www.gnu.org/licenses/agpl-3.0.en.html

[fork-shield]: https://img.shields.io/badge/repository-fork-blue

[fork-url]: https://gitlab.com/2009scape/2009scape

[play-release]: https://img.shields.io/badge/singleplayer-release-blue

[play-url]: https://github.com/szumaster1/game

[kotlin-version]: https://img.shields.io/badge/kotlin-1.8.20-blue.svg?logo=kotlin

[kotlin-url]: http://kotlinlang.org

[java-version]: https://img.shields.io/badge/java-11-blue.svg?logo=openjdk

[java-url]: https://adoptium.net/temurin/releases/?version=11