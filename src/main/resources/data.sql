INSERT IGNORE INTO offices (id, city, name, number, street) VALUES (1, 'Wrocław', 'K', 48, 'Legnicka');
INSERT IGNORE INTO offices (id, city, name, number, street) VALUES (2, 'Wrocław', 'H', 48, 'Legnicka');

INSERT IGNORE INTO payments (id, name) VALUES (1, 'Cash');
INSERT IGNORE INTO payments (id, name) VALUES (2, 'Blik');
INSERT IGNORE INTO payments (id, name) VALUES (3, 'Bank transfer');

INSERT IGNORE INTO notifications (id, content, name) VALUES (1, 'Your order status has changed to: Collecting Suborders!', 'COLLECTING_SUBORDERS_NOTIFICATION');
INSERT IGNORE INTO notifications (id, content, name) VALUES (2, 'Your meal has been ordered and it is waiting for realisation!', 'WAITING_FOR_REALISATION_NOTIFICATION');
INSERT IGNORE INTO notifications (id, content, name) VALUES (3, 'Your order has been delivered and it is ready to eat in the kitchen!', 'FOR_PICK_UP_NOTIFICATION');
INSERT IGNORE INTO notifications (id, content, name) VALUES (4, 'Your order is closed. Have a nice day! :)', 'CLOSED_NOTIFICATION');
INSERT IGNORE INTO notifications (id, content, name) VALUES (5, 'Your order has been canceled. For more information contact order owner.', 'CANCELED_NOTIFICATION');