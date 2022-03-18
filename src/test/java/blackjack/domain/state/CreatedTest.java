package blackjack.domain.state;

import static blackjack.domain.card.CardNumber.JACK;
import static blackjack.domain.card.CardNumber.KING;
import static blackjack.domain.card.CardNumber.QUEEN;
import static blackjack.domain.card.CardPattern.SPADE;
import static org.assertj.core.api.Assertions.assertThat;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.game.BettingMoney;
import blackjack.domain.state.finished.Bust;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreatedTest {

    @Test
    @DisplayName("카드들을 반환한다.")
    void getCards() {
        final List<Card> cards = Arrays.asList(Card.of(SPADE, KING), Card.of(SPADE, QUEEN), Card.of(SPADE, JACK));
        final State state = new Bust(new Cards(cards), new BettingMoney(2000));
        assertThat(state.cards().getValues()).isEqualTo(cards);
    }
}
