package model.shop.upgrades;

import model.region.regions.Region;
import model.shop.Points;
import model.transport.TransportType;

import javax.swing.*;
import java.util.Optional;
import java.util.function.Predicate;

public class UpgradeUtils {
    private static final Points pointsModel = Points.getInstance();

    public static boolean spendPoints(int cost) {
        int currentPoints = pointsModel.getAmount();
        if ((currentPoints - cost) >= 0) {
            pointsModel.decreasePoints(cost);
            return true;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Not enough points!",
                    "Insufficient Balance",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Optional<Region> showAllRegionSelectionDialog() {
        Region[] regions = Region.getRegionExtent().values().toArray(new Region[0]);
        Region selectedRegion = (Region) JOptionPane.showInputDialog(
                null,
                "Select a region to apply effect:",
                "Region Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                regions,
                regions[0]
        );
        return Optional.ofNullable(selectedRegion);
    }

    public static Optional<Region> showFilteredRegionSelectionDialog(Predicate<Region> predicate) {
        Region[] regions = Region.getRegionExtent().values().stream()
                .filter(predicate)
                .toArray(Region[]::new);

        if (regions.length == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "No regions available.",
                    "Attention!",
                    JOptionPane.INFORMATION_MESSAGE);
            return Optional.empty();
        }

        Region selectedRegion = (Region) JOptionPane.showInputDialog(
                null,
                "Select a region to apply effect:",
                "Region Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                regions,
                regions[0]
        );
        return Optional.ofNullable(selectedRegion);
    }

    public static Optional<TransportType> showTransportTypeSelectionDialog() {
        TransportType[] transportTypes = TransportType.values();

        TransportType selectedType = (TransportType) JOptionPane.showInputDialog(
                null,
                "Select a transport type to close borders:",
                "Transport Type Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                transportTypes,
                transportTypes[0]
        );

        return Optional.ofNullable(selectedType);
    }
}
