SET FOREIGN_KEY_CHECKS = 0;



-- 2. tb_project
INSERT INTO `tb_project` (`name`) VALUES
    ('Project A'),
('Project B'),
('Project C');






-- 4. tb_project_folder (project_id 참조)
INSERT INTO `tb_project_folder` (`project_id`, `name`) VALUES
    (1, 'Folder A'),
(2, 'Folder B'),
(3, 'Folder C');

-- 6. tb_lang_model (project_id 참조)
INSERT INTO `tb_lang_model` (`project_id`, `name`, `vendor`, `feature`, `api_key`) VALUES
    (1, 'GPT-3.5', 'OpenAI', 'CHAT',      'key1'),
(2, 'GPT-4',   'OpenAI', 'EMBEDDING', 'key2'),
(3, 'KoGPT',   'Kakao',  'RERANK',    'key3');

-- 7. tb_model_preset (lang_model_id 참조)
INSERT INTO `tb_model_preset` (`lang_model_id`, `temperature`, `top_p`, `top_k`) VALUES
    (1, 0.7,  0.9,   50),
(2, 0.8,  0.85,  40),
(3, 1.0,  1.0,   60);

-- 8. tb_conf_knowledge (project_id 참조)
INSERT INTO `tb_conf_knowledge`
(`project_id`, `chk_token_size`, `overlap_token_rate`, `emb_model_name`, `rerk_model_name`, `rerk_top_n`, `retv_theshold_score`, `retv_top_k`, `keyword_weight`)
VALUES
    (1, '2048', 0.10, 'openai-embed-ada',     'openai-rerank', 3, 80, 50, 1.0),
(2, '1024', 0.20, 'openai-embed-babbage', 'openai-rerank', 5, 75, 40, 1.5),
(3, '4096', 0.15, 'kakao-embed',          'kakao-rerank',  4, 85, 30, 2.0);

-- 9. tb_synonym (project_id 참조)
INSERT INTO `tb_synonym` (`project_id`, `source`, `match`) VALUES
    (1, 'happy',    'glad'),
(2, 'fast',     'quick'),
(3, 'powerful', 'strong');

-- 10. tb_homonym (project_id 참조)
INSERT INTO `tb_homonym` (`project_id`, `source`, `match`) VALUES
    (1, 'bank', 'river_bank'),
(2, 'bank', 'financial_institution'),
(3, 'bark', 'tree_skin');

-- 11. tb_conversation (project_id 참조)
INSERT INTO `tb_conversation` (`project_id`, `title`) VALUES
    (1, 'Chat about Project A'),
(2, 'Discussion on Project B'),
(3, 'Conversation for Project C');

-- 12. tb_question (conversation_id 참조)
INSERT INTO `tb_question` (`conversation_id`, `message`) VALUES
    (1, 'What is the status of Project A?'),
(2, 'When can we deploy Project B?'),
(3, 'Any updates on Project C?');

-- 13. tb_answer (question_id 참조)
INSERT INTO `tb_answer` (`question_id`, `message`, `pipeline_log`) VALUES
    (1, 'Project A is on schedule.',              'Log A'),
(2, 'Project B will be deployed next month.', 'Log B'),
(3, 'Project C has no recent updates.',       'Log C');

-- 14. tb_feedback (answer_id 참조)
INSERT INTO `tb_feedback` (`answer_id`, `feedback_type`, `comment`) VALUES
    (1, 'GOOD',       'Helpful answer'),
(2, 'BAD',        'Needs more detail'),
(3, 'ETC',        'Thanks for the info');

-- 15. tb_doc (project_folder_id 참조)
INSERT INTO `tb_doc` (`project_folder_id`, `name`, `page_count`, `state`, `url`,`file_size`, `revision`) VALUES
    (1, 'Doc A', 10, 'PENDING',  'http://example.com/docA.pdf',23, 'revA'),
(2, 'Doc B', 15, 'SERVING',  'http://example.com/docB.pdf', 41,'revB'),
(3, 'Doc C', 20, 'INACTIVE', 'http://example.com/docC.pdf', 45,'revC');

-- 16. tb_doc_account (doc_id, account_id 참조)
INSERT INTO `tb_doc_account` (`doc_id`, `account_id`) VALUES
    (1, 'acc1'),
(2, 'acc2'),
(3, 'acc3');

-- 17. tb_doc_attr (doc_id 참조)
INSERT INTO `tb_doc_attr` (`doc_id`, `name`) VALUES
    (1, 'Attribute A'),
(2, 'Attribute B'),
(3, 'Attribute C');

-- 18. tb_doc_attr_hierarchy (ancestor_id, descendant_id 참조)
INSERT INTO `tb_doc_attr_hierarchy` (`ancestor_id`, `descendant_id`, `depth`) VALUES
    (1, 2, 1),
(1, 3, 2),
(2, 3, 1);

-- 19. tb_doc_node (doc_attr_id 참조)
INSERT INTO `tb_doc_node` (`doc_attr_id`) VALUES
    (1),
(2),
(3);

-- 20. tb_doc_hierarchy (from_node_id, to_node_id 참조)
INSERT INTO `tb_doc_hierarchy` (`from_node_id`, `to_node_id`, `edge`, `weight`) VALUES
    (1, 2, 'Edge A', 1),
(1, 3, 'Edge B', 1),
(2, 3, 'Edge C', 1);

-- 21. tb_similarity_doc (answer_id, doc_id 참조)
INSERT INTO `tb_similarity_doc` (`answer_id`, `doc_id`, `page`, `score`) VALUES
    (1, 1, 1, 95.5),
(2, 2, 2, 80.2),
(3, 3, 3, 70.1);

-- 22. tb_doc_part (doc_id 참조)
INSERT INTO `tb_doc_part` (`doc_id`, `title`, `chapter_type`, `start_page`, `end_page`, `label`) VALUES
    (1, 'Introduction', 'CONTENTS', 1,  2, 'Intro Label'),
(2, 'Chapter 1',    'CHAPTER',  3,  5, 'Chapter1 Label'),
(3, 'Chapter 2',    'CHAPTER',  6, 10, 'Chapter2 Label');

-- 23. tb_doc_image (doc_id 참조)
INSERT INTO `tb_doc_image` (`doc_id`, `page`) VALUES
    (1, 2),
(2, 4),
(3, 6);

-- 24. tb_doc_keyword (doc_id 참조)
INSERT INTO `tb_doc_keyword` (`doc_id`, `keyword`, `count`) VALUES
    (1, 'KeywordA', 5),
(2, 'KeywordB', 3),
(3, 'KeywordC', 7);

SET FOREIGN_KEY_CHECKS = 1;
