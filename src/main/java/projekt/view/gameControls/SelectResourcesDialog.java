package projekt.view.gameControls;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import projekt.model.Player;
import projekt.model.ResourceType;
import projekt.view.ResourceCardPane;

import java.util.HashMap;
import java.util.Map;

/**
 * A dialog to prompt the user to select a number of resources.
 * The dialog shows the resources the player can choose from and lets the user
 * select a number of each resource.
 * If dropCards is true, the user is prompted to drop cards instead of selecting
 * them.
 * The result of the dialog is a map of the selected resources and their
 * amounts.
 */
public class SelectResourcesDialog extends Dialog<Map<ResourceType, Integer>> {

    /**
     * Creates a new SelectResourcesDialog for the given player and resources.
     *
     * @param amountToSelect        The amount of resources to select.
     * @param player                The player that is prompted to select resources.
     * @param resourcesToSelectFrom The resources the player can select from. If
     *                              null the player can select any resource.
     * @param dropCards             Whether the player should drop cards instead of
     *                              selecting them.
     */
    public SelectResourcesDialog(
        final int amountToSelect, final Player player,
        final Map<ResourceType, Integer> resourcesToSelectFrom, final boolean dropCards
    ) {
        final DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);
        dialogPane.setContent(init(amountToSelect, player, resourcesToSelectFrom, dropCards));
    }

    @StudentImplementationRequired("H3.3")
    private Region init(
        final int amountToSelect,
        final Player player,
        Map<ResourceType, Integer> resourcesToSelectFrom, final boolean dropCards
    ) {

        // Initial Setup
        GridPane gridPane = new GridPane();

        VBox vBox = new VBox();
        gridPane.getChildren().add(vBox);

        vBox.getChildren().add(new Text("Select resources"));
        if (dropCards)
            vBox.getChildren().add(new Text("These resources will be dropped!"));

        HBox hBox = new HBox();
        vBox.getChildren().add(hBox);

        Map<ResourceType, Integer> map = new HashMap<>();

        if (resourcesToSelectFrom == null) resourcesToSelectFrom = player.getResources();
        Text selected = new Text(selectedCounter(0, amountToSelect));

        resourcesToSelectFrom
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(((it) -> {

                ResourceType resourceType = it.getKey();
                int amount = it.getValue();

                VBox localvBox = new VBox();
                hBox.getChildren().add(localvBox);
                localvBox.getChildren().add(new ResourceCardPane(resourceType, amount));

                Slider slider = new Slider(0, amount, 0);
                slider.setMajorTickUnit(2.0);
                slider.setBlockIncrement(1.0);
                slider.setShowTickLabels(true);
                slider.setShowTickMarks(true);
                Text sliderText = new Text("0");
                slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    slider.setValue(newValue.intValue());
                    map.put(resourceType, newValue.intValue());
                    sliderText.setText(String.valueOf(newValue.intValue()));
                    int mapSum = map.values().stream().mapToInt(Integer::intValue).sum();
                    selected.setText(selectedCounter(mapSum, amountToSelect));
                    getDialogPane().lookupButton(ButtonType.OK).setDisable(mapSum != amountToSelect);
                });
                localvBox.getChildren().add(slider);
                localvBox.getChildren().add(sliderText);
            }));

        vBox.getChildren().add(selected);

        getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {});

        getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        setResultConverter(button -> map);
        return gridPane;
    }

    private String selectedCounter(int selected, int amountToSelect) {
        return String.format("Selected: %d / %d", selected, amountToSelect);
    }
}
