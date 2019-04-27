package fr.jcgay.notification.notifier.notificationcenter

import fr.jcgay.notification.Notification
import fr.jcgay.notification.TestIcon
import fr.jcgay.notification.executor.FakeExecutor
import spock.lang.Specification
import spock.lang.Subject

class SimpleNotificationCenterSpec extends Specification {

    FakeExecutor executor = new FakeExecutor()

    @Subject
    SimpleNotificationCenterNotifier notifier

    def setup() {
        def configuration = TerminalNotifierConfiguration.create([
            'notifier.notification-center.path':TerminalNotifierConfiguration.byDefault().bin(),
            'notifier.notification-center.sound':'default'
        ] as Properties)
        notifier = new SimpleNotificationCenterNotifier(configuration, executor)
    }

    def "should build command line to send notification"() {
        given:
        def notification = Notification.builder('title', 'message', TestIcon.ok())
            .subtitle('subtitle')
            .build()

        when:
        notifier.send(notification)

        then:
        executor.executedCommand == [
                'osascript',
                '-e',
                /display notification "${notification.message()}" sound name "default" with title "${notification.title()}" subtitle "${notification.subtitle()}"/
        ]
    }

    def "should not set subtitle when notification does not set one"() {
        given:
        def notification = Notification.builder('title', 'message', TestIcon.ok()).build()

        when:
        notifier.send(notification)

        then:
        !executor.executedCommand.any { it.contains('subtitle') }
    }

    def "should not set sound when configuration does not includes one"() {
        given:
        def notifier = new SimpleNotificationCenterNotifier(TerminalNotifierConfiguration.byDefault(), executor)
        def notification = Notification.builder('title', 'message', TestIcon.ok()).build()

        when:
        notifier.send(notification)

        then:
        !executor.executedCommand.any { it.contains('sound name') }
        !executor.executedCommand.any { it.contains('default') }
    }
}
