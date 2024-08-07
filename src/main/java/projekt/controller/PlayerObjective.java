package projekt.controller;

import projekt.controller.actions.*;

import java.util.Set;

/**
 * This enum represents the different objectives a player can have and what
 * actions are allowed when the player has this
 * objective.
 */
public enum PlayerObjective {
    DROP_CARDS(Set.of(SelectCardsAction.class)),
    SELECT_CARD_TO_STEAL(Set.of(StealCardAction.class, EndTurnAction.class)),
    SELECT_ROBBER_TILE(Set.of(SelectRobberTileAction.class)),
    REGULAR_TURN(Set.of(BuildRoadAction.class, EndTurnAction.class, BuildVillageAction.class,
                        UpgradeVillageAction.class, TradeAction.class, BuyDevelopmentCardAction.class,
                        PlayDevelopmentCardAction.class
    )),
    PLACE_VILLAGE(Set.of(BuildVillageAction.class)),
    PLACE_ROAD(Set.of(BuildRoadAction.class)),
    DICE_ROLL(Set.of(RollDiceAction.class)),
    ACCEPT_TRADE(Set.of(AcceptTradeAction.class)),
    SELECT_CARDS(Set.of(SelectCardsAction.class)),
    IDLE(Set.of());

    final Set<Class<? extends PlayerAction>> allowedActions;

    PlayerObjective(final Set<Class<? extends PlayerAction>> allowedActions) {
        this.allowedActions = allowedActions;
    }

    /**
     * Returns the actions that are allowed when the player has this objective.
     *
     * @return the actions that are allowed when the player has this objective
     */
    public Set<Class<? extends PlayerAction>> getAllowedActions() {
        return allowedActions;
    }
}
