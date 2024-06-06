-- Populate expense_participation table
INSERT INTO expense_participation (expense_id, user_id, weight, created_at, updated_at)
VALUES
-- Participations for expenses in Group 1
(1, 1, 2, '2024-03-01', '2024-03-01'),
(1, 2, 1, '2024-03-01', '2024-03-01'),
(2, 2, 2, '2024-03-02', '2024-03-02'),
(2, 3, 1, '2024-03-02', '2024-03-02'),
(3, 3, 2, '2024-03-03', '2024-03-03'),
(3, 1, 1, '2024-03-03', '2024-03-03'),

-- Participations for expenses in Group 2
(4, 2, 2, '2024-03-04', '2024-03-04'),
(4, 3, 1, '2024-03-04', '2024-03-04'),
(5, 3, 2, '2024-03-05', '2024-03-05'),
(5, 4, 1, '2024-03-05', '2024-03-05'),
(6, 4, 2, '2024-03-06', '2024-03-06'),
(6, 2, 1, '2024-03-06', '2024-03-06'),

-- Participations for expenses in Group 3
(7, 3, 2, '2024-03-07', '2024-03-07'),
(7, 4, 1, '2024-03-07', '2024-03-07'),
(8, 4, 2, '2024-03-08', '2024-03-08'),
(8, 5, 1, '2024-03-08', '2024-03-08'),
(9, 5, 2, '2024-03-09', '2024-03-09'),
(9, 3, 1, '2024-03-09', '2024-03-09');