Application.properties not included. Example on how the file should look like below. For more information about the bot, go to twitch.tv/workerbot


src/main/resources/application.properties

spring.datasource.url = 
spring.datasource.username = 
spring.datasource.password = 
 
spring.datasource.testWhileIdle = true
spring.datasource.validationQuery = SELECT 1

spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect

bot.verbiose = true

twitch.oauth = 
twitch.name = 
twitch.server = irc.twitch.tv
twitch.port = 6667
twitch.channeljointimeout = 300
twitch.messageprefix = #
twitch.channelprefix = #
twitch.joinmessage = Hello!
twitch.leavemessage = Good Bye!
twitch.millisecondsbetweenmessages = 1500
twitch.messagebuffer = 5
twitch.globalwhispersettings = true

twitchapi.url = http://tmi.twitch.tv/group/user/USER/chatters

price.ratioitem = Computed Draft Booster Pack
price.ratioaprox = 130

img.host = http://hex.digital-poets.net
img.plugin = http://hex.digital-poets.net/staticImage/USER?cooldown=&in=&out=
img.password = 

game.hex = hex
game.tesl = tesl,esl,legends,tes:l,tes
game.none = none,disable
game.noGameFoundMessage = No matching game found. Current games are 'HEX', 'TES:L' or use 'disable' to turn off commands that are game specific
game.noGameSetMessage = Game specific commands are currently disabled for this channel
game.hexGameMessage = Current game is set to HEX: Shards of Fate
game.teslGameMessage = Current game is set to The Elder Scrolls: Legends
