package blackjack.view.output;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.CardPattern;
import blackjack.dto.CurrentTurnParticipant;
import blackjack.dto.GameResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    private static final String PROVIDE_INIT_CARD_TO_PLAYER_MESSAGE = "%s와 %s에게 2장의 나누었습니다.\n";
    private static final String PROVIDED_CARD_TO_DEALER_INFO_MESSAGE = "%s: %s\n";
    private static final String PROVIDED_CARD_TO_PLAYER_INFO_MESSAGE = "%s카드: %s\n";

    private static final String PROVIDE_CARD_TO_DEALER_MESSAGE = "딜러는 16이하라 한장의 카드를 더 받았습니다.";

    private static final String PLAYER_CARD_RESULT_AND_SCORE_MESSAGE = "%s 카드: %s - 결과: %d\n";

    private static final String PROFIT_MESSAGE = "%s: %d\n";

    private static final String OUTCOME_TITLE = "## 최종 수익";

    private static final String PLAYER_NAME_DELIMITER = ", ";
    private static final String CARD_INFO_DELIMITER = ", ";

    private OutputView() {
        throw new UnsupportedOperationException();
    }

    public static void showGameInitInfo(final CurrentTurnParticipant dealerInfo, final List<CurrentTurnParticipant> currentTurnParticipants) {
        System.out.printf(PROVIDE_INIT_CARD_TO_PLAYER_MESSAGE, dealerInfo.getName(), joinPlayerNames(
                currentTurnParticipants));
        System.out.printf(PROVIDED_CARD_TO_DEALER_INFO_MESSAGE,
                dealerInfo.getName(), joinPlayerCardInfos(dealerInfo.getCards()));
        currentTurnParticipants.forEach(OutputView::printPlayerCardInfo);
    }

    private static String joinPlayerNames(final List<CurrentTurnParticipant> currentTurnParticipants) {
        return currentTurnParticipants.stream()
                .map(CurrentTurnParticipant::getName)
                .collect(Collectors.joining(PLAYER_NAME_DELIMITER));
    }

    private static String joinPlayerCardInfos(final List<Card> cards) {
        return cards.stream()
                .map(card -> joinCardInfo(card.getPattern(), card.getNumber()))
                .collect(Collectors.joining(CARD_INFO_DELIMITER));
    }

    private static String joinCardInfo(final CardPattern pattern, final CardNumber number) {
        return number.getPrintValue() + pattern.getName();
    }

    public static void printPlayerCardInfo(final CurrentTurnParticipant currentTurnParticipant) {
        System.out.printf(PROVIDED_CARD_TO_PLAYER_INFO_MESSAGE,
                currentTurnParticipant.getName(), joinPlayerCardInfos(currentTurnParticipant.getCards()));
    }

    public static void printDealerDraw() {
        System.out.println(PROVIDE_CARD_TO_DEALER_MESSAGE);
    }

    public static void printResultPlayerInfos(final List<GameResult> gameResults) {
        gameResults.forEach(OutputView::printResultPlayerInfo);
    }

    private static void printResultPlayerInfo(final GameResult gameResult) {
        System.out.printf(PLAYER_CARD_RESULT_AND_SCORE_MESSAGE, gameResult.getName(),
                joinPlayerCardInfos(gameResult.getCards()), gameResult.getScore());
    }

    public static void printAllOutcomeResult(final Map<String, Integer> participantsProfit) {
        System.out.println(OUTCOME_TITLE);
        participantsProfit.keySet()
                .forEach(name -> System.out.printf(PROFIT_MESSAGE, name, participantsProfit.get(name)));
    }
}
