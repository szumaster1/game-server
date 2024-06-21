package core.game.container

interface ContainerListener {
    fun update(c: Container?, event: ContainerEvent?)
    fun refresh(c: Container?)
}