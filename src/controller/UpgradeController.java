package controller;

import model.region.regions.Region;
import model.shop.Points;
import model.shop.Upgrade;
import model.transport.TransportType;
import shared.ShopPanel;
import shared.StatsPanel;
import view.UpgradePanel;

import javax.swing.*;
import java.util.Optional;

public class UpgradeController {
    private final Points pointsModel;
    private final UpgradePanel upgradePanel;
    private final StatsPanel statsPanel;

    public UpgradeController(ShopPanel shopPanel, StatsPanel statsPanel) {
        this.pointsModel = Points.getInstance();
        this.upgradePanel = shopPanel.getUpgradePanel();
        this.statsPanel = statsPanel;

        initializeUpgrades();
    }

    private void initializeUpgrades() {
        upgradePanel.addUpgrade(new Upgrade(
                "Global Infection Control",
                "Reduces global infection by 10%.",
                100,
                () -> reduceGlobalInfectionBy(10f)
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Regional Infection Control",
                "Reduces infection by 15% in a selected region.",
                150,
                () -> reduceRegionalInfectionBy(15f)
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Improve cure",
                "Increases cure's efficiency of selected region by 5%.",
                200,
                this::increaseCureEfficiencyByFive
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Eradicate Global Infection",
                "Sets global infection levels to 0.",
                1000,
                this::setGlobalInfectionToZero
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Close All Borders",
                "Closes all borders for selected region.",
                300,
                this::closeAllBordersForCountry
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Close Specific Transport Borders",
                "Closes borders for a specific type of transport for selected region.",
                150,
                this::banSpecificTransportForCountry
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Reduce Infection by 50%",
                "Drops infection by 50% in a selected region.",
                500,
                this::dropInfectionByHalf
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Stop Infection Spread",
                "Stops infection spread and sets infection to 0 in a selected region.",
                800,
                this::applyStopInfectionSpread
        ));

        upgradePanel.addUpgrade(new Upgrade(
                "Destroy Virus",
                "Eliminates all viruses and ends the game.",
                5000,
                this::applyDestroyVirus
        ));
    }

    private boolean spendPoints(int cost) {
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

    private Optional<Region> showRegionSelectionDialog() {
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

    private Optional<TransportType> showTransportTypeSelectionDialog() {
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

    private void reduceGlobalInfectionBy(float subtrahend) {
        if (spendPoints(100)) {
            Region.getRegionExtent().values()
                    .forEach(region -> region.getVirus()
                            .decreaseInfection(subtrahend));

            refreshStatsPanel();
            JOptionPane.showMessageDialog(null,
                    "Global infection reduced by " + subtrahend + "%",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void reduceRegionalInfectionBy(float subtrahend) {
        if (spendPoints(150)) {
            Optional<Region> selectedRegion = showRegionSelectionDialog();

            selectedRegion.ifPresent(region -> {
                region.getVirus().decreaseInfection(subtrahend);

                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + "'s infection reduced by " + subtrahend + "%",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    private void increaseCureEfficiencyByFive() {
        if (spendPoints(200)) {
            Optional<Region> selectedRegion = showRegionSelectionDialog();

            selectedRegion.ifPresent(region -> {
                region.getCure().increaseCureEfficiency(.05f);
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        "Cure efficiency in " + region.getName() + " was increased!",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    private void setGlobalInfectionToZero() {
        if (spendPoints(1000)) {
            Region.getRegionExtent().values()
                    .forEach(region -> region.getVirus()
                            .decreaseInfection(region.getVirus().getInfectionLevel()));

            refreshStatsPanel();
            JOptionPane.showMessageDialog(null,
                    "Global infection level reduced to 0!",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void closeAllBordersForCountry() {
        if (spendPoints(300)) {
            Optional<Region> selectedRegion = showRegionSelectionDialog();

            selectedRegion.ifPresent(region -> {
                region.clearAcceptedTransport();
                statsPanel.setSelectedRegion(region);

                JOptionPane.showMessageDialog(null,
                        region.getName() + "closed all borders",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    private void banSpecificTransportForCountry() {
        if (spendPoints(150)) {
            Optional<Region> selectedRegion = showRegionSelectionDialog();
            Optional<TransportType> selectedTransportType = showTransportTypeSelectionDialog();

            if (selectedRegion.isPresent() && selectedTransportType.isPresent()) {
                selectedRegion.get().removeAcceptedTransportType(selectedTransportType.get());
                statsPanel.setSelectedRegion(selectedRegion.get());

                JOptionPane.showMessageDialog(
                        null,
                        selectedRegion.get().getName() + " banned " + selectedTransportType.get(),
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void dropInfectionByHalf() {
        if (spendPoints(500)) {
            Optional<Region> selectedRegion = showRegionSelectionDialog();

            selectedRegion.ifPresent(region -> {
                region.getVirus().decreaseInfection(region.getVirus().getInfectionLevel() / 2);
                statsPanel.setSelectedRegion(region);

                JOptionPane.showMessageDialog(
                        null,
                        region.getName() + "'s infection reduced by 50%.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    private void applyStopInfectionSpread() {
        if (spendPoints(800)) {
            Optional<Region> selectedRegion = showRegionSelectionDialog();

            selectedRegion.ifPresent(region -> {
                region.getVirus().stop();
                region.getVirus().decreaseInfection(region.getVirus().getInfectionLevel());
                statsPanel.setSelectedRegion(region);

                JOptionPane.showMessageDialog(
                        null,
                        region.getName() + "'s infection spread stopped and set to 0.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    private void applyDestroyVirus() {
        if (spendPoints(5000)) {
            Region.getRegionExtent().values()
                    .forEach(region -> region.getVirus()
                            .setInfectionLevel(0f));

            refreshStatsPanel();

            JOptionPane.showMessageDialog(
                    null,
                    "Virus has been destroyed!",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshStatsPanel() {
        Region selectedRegion = statsPanel.getSelectedRegion();
        if (selectedRegion != null) {
            statsPanel.setSelectedRegion(selectedRegion);
        }
    }
}
