package blackjack.domain.state.finished;

import static blackjack.domain.card.CardNumber.A;
import static blackjack.domain.card.CardNumber.FOUR;
import static blackjack.domain.card.CardNumber.JACK;
import static blackjack.domain.card.CardNumber.KING;
import static blackjack.domain.card.CardNumber.QUEEN;
import static blackjack.domain.card.CardPattern.SPADE;
import static org.assertj.core.api.Assertions.assertThat;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.game.betting.BettingMoney;
import blackjack.domain.state.State;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BlackjackTest {

    @ParameterizedTest
    @DisplayName("다른 State와 비교해 자신의 수익을 반환한다.")
    @MethodSource("provideStateAndExpectedProfit")
    void getProfit(State another, int expected) {
        final BettingMoney bettingMoney = new BettingMoney(2000);
        final Cards cards = new Cards(Arrays.asList(Card.of(SPADE, KING), Card.of(SPADE, QUEEN), Card.of(SPADE, JACK)));
        State state = new Blackjack(cards, bettingMoney);
        assertThat(state.getProfit(another)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideStateAndExpectedProfit() {
        final Cards blackJackCards = new Cards(Collections.singletonList(Card.of(SPADE, A)));
        final Cards stayCards = new Cards(Arrays.asList(Card.of(SPADE, A), Card.of(SPADE, FOUR)));
        final Cards bustCards =
                new Cards(Arrays.asList(Card.of(SPADE, KING), Card.of(SPADE, QUEEN), Card.of(SPADE, JACK)));

        return Stream.of(
                Arguments.of(new Blackjack(blackJackCards, new BettingMoney(2000)), 0),
                Arguments.of(new Stay(stayCards, new BettingMoney(2000)), 3000),
                Arguments.of(new Bust(bustCards, new BettingMoney(2000)), 3000)
        );
    }
}