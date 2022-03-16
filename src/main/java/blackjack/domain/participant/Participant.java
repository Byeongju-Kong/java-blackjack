package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.game.BattingMoney;
import blackjack.domain.participant.vo.PlayerName;
import blackjack.domain.state.State;
import java.util.List;

public abstract class Participant {

    final PlayerName name;
    State state;

    Participant(final String name, final BattingMoney battingMoney, final List<Card> cards) {
        this.name = new PlayerName(name);
        this.state = State.create(new Cards(cards), battingMoney);
    }

    public void draw(final Card card) {
        state = state.draw(card);
    }

    public String getName() {
        return name.getValue();
    }

    public void stay() {
        state = state.stay();
    }

    public int calculateScore() {
        checkTurnOver();
        return state.cards().getScore();
    }

    private void checkTurnOver() {
        if (canDraw()) {
            throw new IllegalStateException("턴이 종료되지 않아 카드의 합을 반환할 수 없습니다.");
        }
    }

    public abstract List<Card> getInitCards();

    public abstract List<Card> getCards();

    public abstract boolean canDraw();
}
