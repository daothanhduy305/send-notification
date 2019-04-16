# 0.16.0
***

- Disable auto-detect for BurntToast ([03952d5](http://github.com/jcgay/send-notification/commit/03952d5d8fbbd776c10b4e5ecd2d4ed94d53456d)) by [Sean Flanigan](https://github.com/seanf)

# 0.15.1
***

- Removed no longer existing parameter for BurntNotificationToast '-AppId' [Fixes [#11](https://github.com/jcgay/send-notification/issues/11)] ([89c84e3](http://github.com/jcgay/send-notification/commit/89c84e3f0350c68163d8293c63134901df203d10)) by [@JD-CSTx](https://github.com/JD-CSTx)

# 0.15.0
***

- Use jPowerShell 3.0.1 [Fixes [#10](https://github.com/jcgay/send-notification/issues/10)] ([b67f9bd](http://github.com/jcgay/send-notification/commit/b67f9bda4e8290468328ab10b97fc3c5a1b1c37b))
- Migrate to Java 8 ([c728c5e](http://github.com/jcgay/send-notification/commit/c728c5e9b8186155e5827cc7ae114945fdb923dc))
- Move windows vm to gusztavvargadr/w10e ([a8ef16c](http://github.com/jcgay/send-notification/commit/a8ef16ca829d92feb35e612704797ec9a512b586))

# 0.14.0
***

- Default RuntimeExecutor timeout is now 500 ms ([8d792b5](http://github.com/jcgay/send-notification/commit/8d792b5642264403b9072f61cd2c2d8d4e642598))
- Revert "Set timeout parameter for terminal-notifier" ([0a26ad5](http://github.com/jcgay/send-notification/commit/0a26ad5c15940180dde75ae7bbd414463ee8849a))
- Timeout in RuntimeExecutor is now set from Application configuration ([9d0fb02](http://github.com/jcgay/send-notification/commit/9d0fb0226df88cba08a9549f4ded2150052b3134))
- Close notifier in *Example ([95a2a45](http://github.com/jcgay/send-notification/commit/95a2a45c2d3d467ea962bc988c5094e774df2022))
- Set timeout parameter for terminal-notifier ([c5fcb9c](http://github.com/jcgay/send-notification/commit/c5fcb9c75c35cbc83f9a88349baaebd9745bcd5c))
- Do not set a default app activation for terminal-notifier ([61f142f](http://github.com/jcgay/send-notification/commit/61f142f0940542117a3494e034bb723797337faf))

# 0.13.0
***

- Provide a default Application ([8ee5e65](http://github.com/jcgay/send-notification/commit/8ee5e65c46907dae49190131656a798ee0d55331))
- Log a warning when a configured notifier is not valid ([4e227b4](http://github.com/jcgay/send-notification/commit/4e227b440cd1c87518b35a41400892580e3afcb2))
- Ignore unknown certificates when installing Windows notifiers ([c37e1d4](http://github.com/jcgay/send-notification/commit/c37e1d4a66bc847ff567cba278b235c461ca5243))
- Fail when a command returns a code != 0 ([76e7f4f](http://github.com/jcgay/send-notification/commit/76e7f4fbf7a456c36dbd242a76d0400aa4652f20))
- Remove duplicated code by introducing Executor#tryExec ([6b1faaf](http://github.com/jcgay/send-notification/commit/6b1faaf659254541d2ad53b094eb35dd18261247))

# 0.12.0
***

- Can set email when sending notification with Pushbullet ([0510467](http://github.com/jcgay/send-notification/commit/051046716fbef76873b6623a9b897a88a18aea96))

# 0.11.0
***

- Add Slack notifier ([613e0af](http://github.com/jcgay/send-notification/commit/613e0af8ad444b89f231a26e36e800efef8f26e2))
- Add BurntToast notifier ([00af537](http://github.com/jcgay/send-notification/commit/00af5378207297374f8b9c42feb7ebd149a6498d))
- Add Notify notifier ([f6c190d](http://github.com/jcgay/send-notification/commit/f6c190dddb8160996ae84372b11bd20cb1fc8e5a))

# 0.10.2
***

- Replace Objects#toStringHelper() deprecated calls ([0dc3234](http://github.com/jcgay/send-notification/commit/0dc32340f289511c51b74bdc626f044c74fbea9a))
- Fail when pushbullet credentials are not valid ([a62638f](http://github.com/jcgay/send-notification/commit/a62638f77adaa12c6a5be53e02ea22bf0880eead))

# 0.10.1
***

- Escape argument when executing notifu ([41358dd](http://github.com/jcgay/send-notification/commit/41358ddc20125d35996ebba5545c00e2b66ff31f))

# 0.10
***

- Prevent dock icon creation on OS X ([f7ba636](http://github.com/jcgay/send-notification/commit/f7ba63631fe6e1c9f2bbad126164eeca1cf2d7b5))

# 0.9
***

- Better log messages ([1dbc5a2](http://github.com/jcgay/send-notification/commit/1dbc5a2129abb8263bafe9da92943f27b0d462c0))
- Can set Snarl application password ([c2e5d3b](http://github.com/jcgay/send-notification/commit/c2e5d3bb33964a737683a2da819f9a8d7a9df49e))
- Register Snarl application before each notification ([0e8e719](http://github.com/jcgay/send-notification/commit/0e8e71923a3fd1c0e350f974b1521638cfaa1865))

# 0.8
***

- Use notifier 'none' to not send notifications ([8b30bc2](http://github.com/jcgay/send-notification/commit/8b30bc2c03e800b36eb8a455172fbe1b1388f638))
- Log Growl errors in debug when used in auto discovery mode ([24aa149](http://github.com/jcgay/send-notification/commit/24aa149ad33804fe69164c9b18b0c1ec2a5fbb02))

# 0.7
***

- Indicate if a notifier is persistent ([3b21eb2](http://github.com/jcgay/send-notification/commit/3b21eb292fd329f680a9141319ee3b24a9e874f3))
- Can send notification to multiple implementations at once ([7cf3ccc](http://github.com/jcgay/send-notification/commit/7cf3ccccf9c3e06c14f925e81e4674a3030738ad))

# 0.6
***

- Can automatically select notifier if they are available ([3c0339c](http://github.com/jcgay/send-notification/commit/3c0339c7397446a38d820e69a1101b82bead4131))

# 0.5
***

- Add Toaster notifier for Windows > 8 ([d6f2a80](http://github.com/jcgay/send-notification/commit/d6f2a80627965918f2350a142d03438f2a792c9c))
- Add notification center notifier using AppleScript ([8d070c1](http://github.com/jcgay/send-notification/commit/8d070c153326b0ddb44b39512ed26f7b04cf44c4))
- Can set application icon using terminal-notifier ([7709750](http://github.com/jcgay/send-notification/commit/77097506e1d678355570929f95b1d40b3209525f))
- Can set sound using terminal-notifier ([257ca55](http://github.com/jcgay/send-notification/commit/257ca55dde704fb4a67badd8cdd105c430aed5ea))
- Package generated sources in sources.jar ([589caf6](http://github.com/jcgay/send-notification/commit/589caf6902253399186020f8cf4f96c80c05baa1))

# 0.4
***

- Add AnyBar notifier [view](http://github.com/jcgay/send-notification/commit/a7d91fdefdaf44ad86c3f596d909cf08d8400ad5)

# 0.3
***

- Lower log statements level [view](http://github.com/jcgay/send-notification/commit/08247050ced8b99f438b4bb4e3cda9bdbdb84f7a)  
- Add Kdialog notifier [view](http://github.com/jcgay/send-notification/commit/7af0f9d854085f304458e342c50348505d200519)  
- Add notifu notifier [view](http://github.com/jcgay/send-notification/commit/9ce1161e689eca678a058fa1b8af7b40e2767522)  
