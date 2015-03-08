package pokey.application.module

import akka.actor.{ActorRef, ActorSystem}
import play.api.Configuration
import pokey.room.actor.RoomRegistry
import pokey.room.service.{DefaultRoomService, RoomService}
import pokey.user.actor.{UserProxyActor, UserRegistry}
import pokey.user.model.User
import pokey.user.service.{DefaultUserService, UserService}
import scaldi.Module

class ServiceModule extends Module {
  bind [UserService] to injected [DefaultUserService] (
    'userRegistry -> inject [ActorRef] (identified by UserRegistry.identifier)
  )

  bind[UserProxyActor.PropsFactory] to {
    val config = inject [Configuration]
    def settings: UserProxyActor.Settings = UserProxyActor.defaultSettings

    (user: User) => UserProxyActor.props(settings, user)
  }

  bind [ActorRef] identifiedBy required(UserRegistry.identifier) to {
    implicit val system = inject [ActorSystem]
    val userProxyProps = inject [UserProxyActor.PropsFactory]

    system.actorOf(UserRegistry.props(userProxyProps), "user-registry")
  }

  bind [RoomService] to injected [DefaultRoomService] (
    'roomRegistry -> inject [ActorRef] (identified by RoomRegistry.identifier)
  )

  bind [ActorRef] identifiedBy required(RoomRegistry.identifier) to {
    implicit val system = inject [ActorSystem]
    val userService = inject [UserService]

    system.actorOf(RoomRegistry.props(userService), "room-registry")
  }

}
