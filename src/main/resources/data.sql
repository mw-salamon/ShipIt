INSERT IGNORE INTO offices (id, city, name, number, street) VALUES (1, "Wrocław", "K", 48, "Legnicka");
INSERT IGNORE INTO offices (id, city, name, number, street) VALUES (2, "Wrocław", "H", 48, "Legnicka");

INSERT IGNORE INTO payments (id, name) VALUES (1, "Cash");
INSERT IGNORE INTO payments (id, name) VALUES (2, "Blik");
INSERT IGNORE INTO payments (id, name) VALUES (3, "Bank transfer");

INSERT IGNORE INTO notifications (id, content, name) VALUES (2, 'Your order status has changed to: Collecting Suborders!', 'COLLECTING_SUBORDERS_NOTIFICATION');