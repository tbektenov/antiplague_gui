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
import java.util.function.Predicate;

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
                "Develop cure",
                "Selected region initializes cure development.",
                100,
                this::startDevelopingCure
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
                "Create Antidote",
                "Erases virus from this world.",
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

    private Optional<Region> showAllRegionSelectionDialog() {
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

    private Optional<Region> showFilteredRegionSelectionDialog(Predicate<Region> predicate) {
        Region[] regions = Region.getRegionExtent().values().stream()
                .filter(predicate)
                .toArray(Region[]::new);

        Region selectedRegion;
        if (regions.length == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "No regions available",
                    "Attention!",
                    JOptionPane.INFORMATION_MESSAGE);
            return Optional.empty();
        } else {
            selectedRegion = (Region) JOptionPane.showInputDialog(
                    null,
                    "Select a region to apply effect:",
                    "Region Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    regions,
                    regions[0]
            );
        }

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

    private void startDevelopingCure() {
        Optional<Region> selectedRegion = showFilteredRegionSelectionDialog(
                region -> region.getCureEfficiency() == 0
        );

        selectedRegion.ifPresent(region -> {
            if (spendPoints(100)) {
                region.getCure().startDevelopingCure();

                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + " has started developing cure",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    private void reduceRegionalInfectionBy(float subtrahend) {
        Optional<Region> selectedRegion = showAllRegionSelectionDialog();

        selectedRegion.ifPresent(region -> {
            if (spendPoints(150)) {
                region.getVirus().decreaseInfection(subtrahend);

                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + "'s infection reduced by " + subtrahend + "%",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    private void increaseCureEfficiencyByFive() {
        Optional<Region> selectedRegion = showFilteredRegionSelectionDialog(
                region -> region.getCureEfficiency() != 0
        );

        selectedRegion.ifPresent(region -> {
            if (spendPoints(200)) {
                region.getCure().increaseCureEfficiency(0.05f);
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        "Cure efficiency in " + region.getName() + " was increased!",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
        Optional<Region> selectedRegion = showAllRegionSelectionDialog();

        selectedRegion.ifPresent(region -> {
            if (spendPoints(300)) {
                region.clearAcceptedTransport();
                statsPanel.setSelectedRegion(region);

                JOptionPane.showMessageDialog(null,
                        region.getName() + "closed all borders",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void banSpecificTransportForCountry() {
        Optional<Region> selectedRegion = showAllRegionSelectionDialog();
        Optional<TransportType> selectedTransportType = showTransportTypeSelectionDialog();

        if (selectedRegion.isPresent() && selectedTransportType.isPresent()) {
            if (spendPoints(150)) {
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
            Optional<Region> selectedRegion = showAllRegionSelectionDialog();

            selectedRegion.ifPresent(region -> {
                if (spendPoints(500)) {
                    region.getVirus().decreaseInfection(region.getVirus().getInfectionLevel() / 2);
                    statsPanel.setSelectedRegion(region);

                    JOptionPane.showMessageDialog(
                            null,
                            region.getName() + "'s infection reduced by 50%.",
                            "Upgrade Acquired",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
    }

    private void applyStopInfectionSpread() {
        if (spendPoints(800)) {
            Optional<Region> selectedRegion = showAllRegionSelectionDialog();

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
                    .forEach(region -> {
                        region.getVirus()
                                .setInfectionLevel(0f);
                        region.getVirus().stop();

                        region.getCure()
                                .setCureEfficiency(1f);
                    });

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
