package com.example.battleroyal;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Arena  {
    private Scene scene;
    private Fighter f1;
    private Fighter f2;
    private Label l1;
    private Label l2;
    private Label l3;
    private Label l4;
    private StackPane root;
    private Pane arenaLayer;
    private Pane projectileLayer;
    private boolean gameOver = false;
    private AnimationTimer gameLoop;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Set<javafx.scene.input.KeyCode> keys = new HashSet<>();
    private boolean canShootP1=true;
    private boolean canShootP2=true;
    private boolean changeweaponp1 = true;
    private boolean changeweaponp2 =true;


    public Arena(Stage stage, String fighter1Type, String fighter2Type) {
        root = new StackPane();
        arenaLayer = new Pane();
        projectileLayer = new Pane();
        root.getChildren().addAll(arenaLayer, projectileLayer);
        projectileLayer.setPickOnBounds(false);
        projectileLayer.toFront();
        stage.setResizable(false);
        arenaLayer.setStyle("-fx-background-color: #1e1e2f;");
        Line mid = new Line();
        mid.setStroke(Color.BLUE);
        mid.setStrokeWidth(3);
        mid.startXProperty().bind(root.widthProperty().divide(2));
        mid.endXProperty().bind(root.widthProperty().divide(2));
        mid.startYProperty().set(0);
        mid.endYProperty().bind(root.heightProperty());

        f1 = createFighter(fighter1Type, "Player 1", 50, 200 );
        f2 = createFighter(fighter2Type, "Player 2", 500, 200);
        l1 = new Label("health f1: " + f1.getHealth());
        l2 = new Label("health f2: " + f2.getHealth());
        l3 = new Label("weapon " + f1.getWeapon().getName());
        l4 = new Label("weapon" + f2.getWeapon().getName());
        l1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: violet;");
        l2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: violet;");
        l3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        l4.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        l1.setLayoutX(10);
        l1.setLayoutY(10);
        l2.setLayoutX(500);
        l2.setLayoutY(10);
        l3.setLayoutX(30);
        l3.setLayoutY(30);
        l4.setLayoutX(460);
        l4.setLayoutY(30);

        arenaLayer.getChildren().addAll(mid, f1.getSprite(), f2.getSprite(), l1, l2,l3,l4);
        scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        scene.setOnKeyPressed(e -> keys.add(e.getCode()));
        scene.setOnKeyReleased(e -> keys.remove(e.getCode()));
        startGameLoop(); }
    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOver) return;
                if (keys.contains(KeyCode.LEFT)) f2.moveLeft();
                if (keys.contains(KeyCode.RIGHT)) f2.moveRight();
                if (keys.contains(KeyCode.UP)) f2.moveUp();
                if (keys.contains(KeyCode.DOWN)) f2.moveDown();
                if (keys.contains(KeyCode.SPACE)&&canShootP1){shootProjectile(f1, f2);canShootP1=false;}
                if(!keys.contains(KeyCode.SPACE)){canShootP1=true;}
                if (keys.contains(KeyCode.Q)&&changeweaponp1) {
                    f1.nextWeapon();
                    l3.setText("Weapon: " + f1.getWeapon().getName());
                    changeweaponp1=false; }
                if(!keys.contains(KeyCode.Q)){changeweaponp1=true;}

                if (keys.contains(KeyCode.P)&&changeweaponp2) {
                    f2.nextWeapon();
                    l4.setText("Weapon: " + f2.getWeapon().getName());
                    changeweaponp2=false;
                }
                if(!keys.contains(KeyCode.P)){changeweaponp2=true;}
                if (keys.contains(KeyCode.A)) f1.moveLeft();
                if (keys.contains(KeyCode.D)) f1.moveRight();
                if (keys.contains(KeyCode.W)) f1.moveUp();
                if (keys.contains(KeyCode.S)) f1.moveDown();
                if (keys.contains(KeyCode.ENTER)&&canShootP2) {shootProjectile(f2, f1);canShootP2=false;}
                if(!keys.contains(KeyCode.ENTER)){canShootP2=true;}
                for (int i = projectiles.size() - 1; i >= 0; i--) {
                    Projectile p = projectiles.get(i);
                    p.update();
                    if (p.isActive() && p.collidesWith(f1) && p.getShooter() != f1) {
                        f1.healthDamage(p.getDamage());
                        p.setActive(false);
                        updateHealthLabels();
                    }

                    if (p.isActive() && p.collidesWith(f2) && p.getShooter() != f2) {
                        f2.healthDamage(p.getDamage());
                        p.setActive(false);
                        updateHealthLabels();
                    }

                    if (!p.isActive()) {
                        projectileLayer.getChildren().remove(p);
                        projectiles.remove(i);
                    }
                }

                if (f1.isDied() || f2.isDied()) {
                    endGame();
                }
            }
        };
        gameLoop.start();
    }

        private Fighter createFighter(String type, String name, double x, double y) {
        switch (type) {
            case "Archer":
                ArrayList<Weapon> ArcherWeapons = new ArrayList<>();
                ArcherWeapons.add(new Bow());
                ArcherWeapons.add(new MagicStaff());
                ArcherWeapons.add(new Canon());
                return new Archer(name, x, y, ArcherWeapons);
            case "Mage":
                ArrayList<Weapon> mageWeapons = new ArrayList<>();
                mageWeapons.add(new MagicStaff());
                mageWeapons.add(new Canon());
                mageWeapons.add(new Bow());
                return new Mage(name, x, y, mageWeapons);
            case "Warrior":
                ArrayList<Weapon> WarriorWeapons = new ArrayList<>();
                WarriorWeapons.add(new Canon());
                WarriorWeapons.add(new Bow());
                WarriorWeapons.add(new MagicStaff());
                return new Warrior(name, x, y, WarriorWeapons);
            default:
                ArrayList<Weapon> warriorWeapons = new ArrayList<>();
                warriorWeapons.add(new Canon());
                warriorWeapons.add(new Bow());
                warriorWeapons.add(new MagicStaff());
                return new Warrior(name, x, y, warriorWeapons);
        }
    }
    private void shootProjectile(Fighter shooter, Fighter target) {
        Projectile p = shooter.shootProjectile(target);
        if (p != null) {
            projectileLayer.getChildren().add(p);
            projectiles.add(p);
            p.toFront();
        }
    }
    private void endGame() {
        gameOver = true;
        if (f1.isDied()) {
            l1.setText("Player 1 Lose (تعيش و تاخد غيرها)");
            l2.setText("Player 2 Win (عاش عليك )");
            l2.setLayoutX(500);
            l2.setLayoutY(10);
        } else {
            l1.setText("Player 1 Win (عاش عليك )");
            l2.setText("Player 2 Lose (تعيش و تاخد غيرها)");
            l2.setLayoutX(320);
            l2.setLayoutY(10);
        }
        keys.clear();
        gameLoop.stop();
    }
    private void updateHealthLabels() {
        l1.setText("health f1: " + f1.getHealth());
        l2.setText("health f2: " + f2.getHealth());
    }
    public Color getColor(String fighter) {
        {
            if (fighter.equals("Mage")) {
                return Color.BLUE;
            } else if (fighter.equals("Archer")) {
                return Color.BROWN;
            } else {
                return Color.VIOLET;
            }
        }
    }



}
