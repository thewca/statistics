-- Competitions
INSERT INTO wca_development.Competitions
    (id, name, cityName, countryId, information, `year`, `month`, `day`, endMonth, endDay, venue, venueAddress, venueDetails, external_website, cellName, showAtAll, latitude, longitude, contact, remarks, registration_open, registration_close, use_wca_registration, guests_enabled, results_posted_at, results_nag_sent_at, generate_website, announced_at, base_entry_fee_lowest_denomination, currency_code, endYear, connected_stripe_account_id, start_date, end_date, enable_donations, competitor_limit_enabled, competitor_limit, competitor_limit_reason, extra_registration_requirements, on_the_spot_registration, on_the_spot_entry_fee_lowest_denomination, refund_policy_percent, refund_policy_limit_date, guests_entry_fee_lowest_denomination, created_at, updated_at, results_submitted_at, early_puzzle_submission, early_puzzle_submission_reason, qualification_results, qualification_results_reason, name_reason, external_registration_page, confirmed_at, event_restrictions, event_restrictions_reason, registration_reminder_sent_at, announced_by, results_posted_by, main_event_id, cancelled_at, cancelled_by, waiting_list_deadline_date, event_change_deadline_date, free_guest_entry_status, allow_registration_edits)
VALUES
    ('WC1982', 'World Rubik''s Cube Championship 1982', 'Budapest', 'Hungary', '', 1982, 6, 5, 6, 5, '', '', '', '', 'World Championship 1982', 1, 47498403, 19040759, '', 'remarks to the board here', NULL, NULL, 0, 1, '1982-06-05 00:00:00', '2016-09-04 14:01:15', NULL, '1982-05-08 00:00:00', NULL, 'USD', 1982, NULL, '1982-06-05', '1982-06-05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1982-06-05 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-15 17:46:45', NULL, NULL, NULL, 1681, 1, '333', NULL, NULL, NULL, NULL, 0, 0),
    ('WC2003', 'World Rubik''s Games Championship 2003', 'Toronto, Ontario', 'Canada', '', 2003, 8, 23, 8, 24, '[Ontario Science Center](http://www.osc.ca)', '', '', 'http://www.speedcubing.com/events/wc2003', 'World Championship 2003', 1, 43716470, -79338711, '', 'remarks to the board here', NULL, NULL, 0, 1, '2003-08-24 00:00:00', '2016-09-04 14:01:16', NULL, '2003-07-26 00:00:00', NULL, 'USD', 2003, NULL, '2003-08-23', '2003-08-24', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2003-08-24 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-15 17:46:45', NULL, NULL, NULL, 125297, 1, '333', NULL, NULL, NULL, NULL, 0, 0),
    ('DutchOpen2003', 'Dutch Open 2003', 'Veldhoven', 'Netherlands', '', 2003, 10, 11, 10, 11, '[ASML](http://www.asml.com)', 'De Run 6501, 5504 DR Veldhoven', 'Building 7', '', 'Dutch Open 2003', 1, 51405966, 5414813, NULL, 'remarks to the board here', NULL, NULL, 0, 1, '2003-10-11 00:00:00', '2016-09-04 14:00:23', NULL, '2003-09-13 00:00:00', NULL, 'USD', 2003, NULL, '2003-10-11', '2003-10-11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2003-10-11 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-15 17:46:45', NULL, NULL, NULL, 125297, 1, '333', NULL, NULL, NULL, NULL, 0, 0);

-- Competition Events
INSERT INTO wca_development.competition_events
    (id, competition_id, event_id, fee_lowest_denomination)
VALUES
    (30814, 'WC1982', '333', 0),
    (30815, 'WC2003', '333', 0),
    (30818, 'WC2003', '333bf', 0),
    (30820, 'WC2003', '333fm', 0),
    (30819, 'WC2003', '333oh', 0),
    (30816, 'WC2003', '444', 0),
    (30825, 'WC2003', '444bf', 0),
    (30817, 'WC2003', '555', 0),
    (30826, 'WC2003', '555bf', 0),
    (30824, 'WC2003', 'clock', 0),
    (30827, 'WC2003', 'magic', 0),
    (30821, 'WC2003', 'minx', 0),
    (30828, 'WC2003', 'mmagic', 0),
    (30822, 'WC2003', 'pyram', 0),
    (30823, 'WC2003', 'sq1', 0),
    (8683, 'DutchOpen2003', '333', 0);

-- Events
INSERT INTO wca_development.Events
    (id, name, `rank`, format, cellName)
VALUES
    ('333', '3x3x3 Cube', 10, 'time', '3x3x3 Cube'),
    ('333bf', '3x3x3 Blindfolded', 70, 'time', '3x3x3 Blindfolded'),
    ('333fm', '3x3x3 Fewest Moves', 80, 'number', '3x3x3 Fewest Moves'),
    ('333oh', '3x3x3 One-Handed', 90, 'time', '3x3x3 One-Handed'),
    ('444', '4x4x4 Cube', 30, 'time', '4x4x4 Cube'),
    ('444bf', '4x4x4 Blindfolded', 160, 'time', '4x4x4 Blindfolded'),
    ('555', '5x5x5 Cube', 40, 'time', '5x5x5 Cube'),
    ('555bf', '5x5x5 Blindfolded', 170, 'time', '5x5x5 Blindfolded'),
    ('clock', 'Clock', 110, 'time', 'Clock'),
    ('magic', 'Magic', 997, 'time', 'Magic'),
    ('minx', 'Megaminx', 120, 'time', 'Megaminx'),
    ('mmagic', 'Master Magic', 998, 'time', 'Master Magic'),
    ('pyram', 'Pyraminx', 130, 'time', 'Pyraminx'),
    ('sq1', 'Square-1', 150, 'time', 'Square-1');
