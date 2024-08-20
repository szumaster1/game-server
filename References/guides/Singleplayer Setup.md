### Cross-platform release of the single-player of this source.

#### Basic information:
- Default xp modes: 1.0x, 25.0x, 500.0x, 1000.0x

#### How to launch
1. Download [github desktop](https://desktop.github.com/download/).
   ![](https://i.imgur.com/RZnyFVo.png)
2. Fork this repository to your repositories, then Clone.
   ![](https://i.imgur.com/GM2vT7k.png)
   ![](https://github.com/user-attachments/assets/96765cd1-e5a4-47f3-8a3b-2b40b1f9a656)<br>
   ![](https://github.com/user-attachments/assets/83b6b35f-35d5-4cc3-a6a5-9c2ebcaa72a8)<br>
4. Run `launch.bat` on Windows, or `launch.sh` on a UN*X system.<br>
   ![](https://i.imgur.com/vKrjVjd.png)
5. If the server starts, run `client.jar`.
   ![](https://i.imgur.com/y7lQ5F7.png)

#### Default config
````
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
msip = "127.0.0.1"
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
smartpathfinder_bfs = true

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
name = "2009scape"
#name used for announcements of bots selling items on the GE.
name_ge = "2009scape"
#Toggle debug mode.
debug = false
#Toggle development mode.
dev = false
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
#Default XP rate (Change it only after you made changes in code).
default_xp_rate = 500.0
#Toggle inauthentic re-roll (Allows you to reset the slayer task 10 times per day).
allow_slayer_reroll = true
#Enables a default clan for players to join automatically. Should be an account with the same name as @name, with a clan set up already.
enable_default_clan = false
#Enable or disable AI bots.
enable_bots = true
#Message of the week model ID, 0 for random.
motw_identifier = "0"
#Text shown for message of the week - @name will be replaced with the name property set above.
motw_text = "Welcome to @name!"
#The coordinates new players spawn at.
new_player_location = "3094,3107,0"
#The location of home teleport.
home_location = "3222,3218,0"
#Auto stock.
autostock_ge = true
#Buy tokens for coins (For FOG, RC Guild shops).
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
jad_practice_enabled = true
#minimum HA value for announcements of bots selling on ge.
ge_announcement_limit = 500
#Toggle castle wars (Not yet).
enable_castle_wars = false
#Enable personalized shops (back-port)
personalized_shops = true
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

````

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

#### FAQ

1. Admin rights:<br>
   You'll need to change config and set `noauth_default_admin = false` to `true`