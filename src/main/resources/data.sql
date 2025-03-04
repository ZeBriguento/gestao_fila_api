/* -- Inserindo dados na tabela tipo_usuario, se estiver vazia
    INSERT INTO tipo_usuario (pk_tipo_usuario, descricao)
    SELECT gen_random_uuid(), 'root'
    WHERE NOT EXISTS (SELECT 1 FROM tipo_usuario);

    INSERT INTO tipo_usuario (pk_tipo_usuario, descricao)
    SELECT gen_random_uuid(), 'admin'
    WHERE NOT EXISTS (SELECT 1 FROM tipo_usuario WHERE descricao = 'admin');

    INSERT INTO tipo_usuario (pk_tipo_usuario, descricao)
    SELECT gen_random_uuid(), 'client'
    WHERE NOT EXISTS (SELECT 1 FROM tipo_usuario WHERE descricao = 'client');

-- Inserindo dados na tabela sexo, se estiver vazia
    INSERT INTO sexo (pk_sexo, descricao)
    SELECT gen_random_uuid(), 'Masculino'
    WHERE NOT EXISTS (SELECT 1 FROM sexo WHERE descricao = 'Masculino');

    INSERT INTO sexo (pk_sexo, descricao)
    SELECT gen_random_uuid(), 'Feminino'
    WHERE NOT EXISTS (SELECT 1 FROM sexo WHERE descricao = 'Feminino');

    INSERT INTO sexo (pk_sexo, descricao)
    SELECT gen_random_uuid(), 'Outro'
    WHERE NOT EXISTS (SELECT 1 FROM sexo WHERE descricao = 'Outro');


-- Criando usuários
    INSERT INTO usuario (pk_usuario, fk_tipo_usuario, username, password)
    SELECT gen_random_uuid(), 
        (SELECT pk_tipo_usuario FROM tipo_usuario WHERE descricao = 'admin'), 
        'admin', 
        'admin'
    WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE username = 'admin');

    INSERT INTO usuario (pk_usuario, fk_tipo_usuario, username, password)
    SELECT gen_random_uuid(), 
        (SELECT pk_tipo_usuario FROM tipo_usuario WHERE descricao = 'client'), 
        'client', 
        'client'
    WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE username = 'client');

    INSERT INTO usuario (pk_usuario, fk_tipo_usuario, username, password)
    SELECT gen_random_uuid(), 
        (SELECT pk_tipo_usuario FROM tipo_usuario WHERE descricao = 'root'), 
        'root', 
        'root'
    WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE username = 'root');

    -- Criando pessoas associadas aos usuários e localidade "Angola"
    INSERT INTO pessoa (pk_pessoa, primeiro_nome, ultimo_nome, numero_identificacao, email, fk_usuario, fk_sexo, fk_local_nascimento)
    SELECT gen_random_uuid(), 
        'João', 
        'Silva', 
        '123456789', 
        'root@email.com', 
        (SELECT pk_usuario FROM usuario WHERE username = 'root'),
        (SELECT pk_sexo FROM sexo WHERE descricao = 'Masculino'),
        (SELECT pk_localidade FROM localidade WHERE nome = 'Cazenga')
    WHERE NOT EXISTS (SELECT 1 FROM pessoa WHERE email = 'root@email.com');

    INSERT INTO pessoa (pk_pessoa, primeiro_nome, ultimo_nome, numero_identificacao, email, fk_usuario, fk_sexo, fk_local_nascimento)
    SELECT gen_random_uuid(), 
        'Maria', 
        'Santos', 
        '987654321', 
        'admin@email.com', 
        (SELECT pk_usuario FROM usuario WHERE username = 'admin'),
        (SELECT pk_sexo FROM sexo WHERE descricao = 'Feminino'),
        (SELECT pk_localidade FROM localidade WHERE nome = 'Belas')
    WHERE NOT EXISTS (SELECT 1 FROM pessoa WHERE email = 'admin@email.com');

    INSERT INTO pessoa (pk_pessoa, primeiro_nome, ultimo_nome, numero_identificacao, email, fk_usuario, fk_sexo, fk_local_nascimento)
    SELECT gen_random_uuid(), 
        'Carlos', 
        'Ferreira', 
        '654987321', 
        'cliente@email.com', 
        (SELECT pk_usuario FROM usuario WHERE username = 'client'),
        (SELECT pk_sexo FROM sexo WHERE descricao = 'Masculino'),
        (SELECT pk_localidade FROM localidade WHERE nome = 'Maputo')
    WHERE NOT EXISTS (SELECT 1 FROM pessoa WHERE email = 'cliente@email.com');

-- Inserindo guichês
    INSERT INTO guiche (pk_guiche, nome, status, fk_usuario)
    SELECT gen_random_uuid(), 'Guichê 1', 'ATIVO', 
        (SELECT pk_usuario FROM usuario WHERE username = 'admin')
    WHERE NOT EXISTS (SELECT 1 FROM guiche WHERE nome = 'Guichê 1');

    INSERT INTO guiche (pk_guiche, nome, status, fk_usuario)
    SELECT gen_random_uuid(), 'Guichê 2', 'INATIVO', 
        (SELECT pk_usuario FROM usuario WHERE username = 'admin')
    WHERE NOT EXISTS (SELECT 1 FROM guiche WHERE nome = 'Guichê 2');

    INSERT INTO guiche (pk_guiche, nome, status, fk_usuario)
    SELECT gen_random_uuid(), 'Guichê 3', 'ATIVO', 
        (SELECT pk_usuario FROM usuario WHERE username = 'admin')
    WHERE NOT EXISTS (SELECT 1 FROM guiche WHERE nome = 'Guichê 3');

-- Inserindo serviços básicos, se não existirem
    INSERT INTO servico (pk_servico, nome, descricao, prioridade, ativo)
    SELECT gen_random_uuid(), 'Atendimento Geral', 'Atendimento para dúvidas e informações.', 'MEDIA', true
    WHERE NOT EXISTS (SELECT 1 FROM servico WHERE nome = 'Atendimento Geral');

    INSERT INTO servico (pk_servico, nome, descricao, prioridade, ativo)
    SELECT gen_random_uuid(), 'Emissão de Documentos', 'Processo de emissão de documentos oficiais.', 'ALTA', false
    WHERE NOT EXISTS (SELECT 1 FROM servico WHERE nome = 'Emissão de Documentos');

    INSERT INTO servico (pk_servico, nome, descricao, prioridade, ativo)
    SELECT gen_random_uuid(), 'Pagamentos e Taxas', 'Recebimento de pagamentos e taxas administrativas.', 'BAIXA', true
    WHERE NOT EXISTS (SELECT 1 FROM servico WHERE nome = 'Pagamentos e Taxas');

    INSERT INTO servico (pk_servico, nome, descricao, prioridade, ativo)
    SELECT gen_random_uuid(), 'Atendimento Prioritário', 'Atendimento para idosos, gestantes e pessoas com deficiência.', 'ALTA', true
    WHERE NOT EXISTS (SELECT 1 FROM servico WHERE nome = 'Atendimento Prioritário');







-- Inserindo dados na tabela localidade, se estiver vazia
    -- Inserindo os países
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Angola', NULL
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Angola');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Moçambique', NULL
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Moçambique');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'África do Sul', NULL
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'África do Sul');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Egito', NULL
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Egito');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Nigéria', NULL
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Nigéria');

        -- Inserindo províncias de Angola
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Luanda', (SELECT pk_localidade FROM localidade WHERE nome = 'Angola')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Luanda');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Benguela', (SELECT pk_localidade FROM localidade WHERE nome = 'Angola')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Benguela');

        -- Inserindo municípios de Luanda (Angola)
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Belas', (SELECT pk_localidade FROM localidade WHERE nome = 'Luanda')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Belas');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Cazenga', (SELECT pk_localidade FROM localidade WHERE nome = 'Luanda')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Cazenga');
    

        -- Inserindo províncias de Moçambique
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Maputo', (SELECT pk_localidade FROM localidade WHERE nome = 'Moçambique')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Maputo');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Sofala', (SELECT pk_localidade FROM localidade WHERE nome = 'Moçambique')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Sofala');

        -- Inserindo municípios de Maputo (Moçambique)
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Matola', (SELECT pk_localidade FROM localidade WHERE nome = 'Maputo')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Matola');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'KaMubukwana', (SELECT pk_localidade FROM localidade WHERE nome = 'Maputo')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'KaMubukwana');

        -- Inserindo províncias da África do Sul
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Gauteng', (SELECT pk_localidade FROM localidade WHERE nome = 'África do Sul')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Gauteng');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Western Cape', (SELECT pk_localidade FROM localidade WHERE nome = 'África do Sul')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Western Cape');

        -- Inserindo municípios de Gauteng (África do Sul)
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Joanesburgo', (SELECT pk_localidade FROM localidade WHERE nome = 'Gauteng')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Joanesburgo');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Pretória', (SELECT pk_localidade FROM localidade WHERE nome = 'Gauteng')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Pretória');

        -- Inserindo províncias do Egito
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Cairo', (SELECT pk_localidade FROM localidade WHERE nome = 'Egito')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Cairo');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Alexandria', (SELECT pk_localidade FROM localidade WHERE nome = 'Egito')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Alexandria');

        -- Inserindo municípios do Cairo (Egito)
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Giza', (SELECT pk_localidade FROM localidade WHERE nome = 'Cairo')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Giza');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Heliópolis', (SELECT pk_localidade FROM localidade WHERE nome = 'Cairo')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Heliópolis');

        -- Inserindo províncias da Nigéria
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Lagos', (SELECT pk_localidade FROM localidade WHERE nome = 'Nigéria')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Lagos');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Abuja', (SELECT pk_localidade FROM localidade WHERE nome = 'Nigéria')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Abuja');

        -- Inserindo municípios de Lagos (Nigéria)
    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Ikeja', (SELECT pk_localidade FROM localidade WHERE nome = 'Lagos')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Ikeja');

    INSERT INTO localidade (pk_localidade, nome, fk_localidade_pai)
    SELECT gen_random_uuid(), 'Victoria Island', (SELECT pk_localidade FROM localidade WHERE nome = 'Lagos')
    WHERE NOT EXISTS (SELECT 1 FROM localidade WHERE nome = 'Victoria Island');

 */

/* -- Inserindo senhas associadas a usuários e serviços
INSERT INTO senha (pk_senha, numero, status, fk_usuario, fk_servico, fk_guiche)
SELECT gen_random_uuid(), 'A001', 'PENDENTE', 
       (SELECT pk_usuario FROM usuario WHERE username = 'client'),
       (SELECT pk_servico FROM servico ORDER BY RANDOM() LIMIT 1),
       (SELECT pk_guiche FROM guiche WHERE nome = 'Guichê 1')
WHERE NOT EXISTS (SELECT 1 FROM senha WHERE numero = 'A001');

INSERT INTO senha (pk_senha, numero, status, fk_usuario, fk_servico, fk_guiche)
SELECT gen_random_uuid(), 'A002', 'EM_ATENDIMENTO', 
       (SELECT pk_usuario FROM usuario WHERE username = 'client'),
       (SELECT pk_servico FROM servico ORDER BY RANDOM() LIMIT 1),
       (SELECT pk_guiche FROM guiche WHERE nome = 'Guichê 1')
WHERE NOT EXISTS (SELECT 1 FROM senha WHERE numero = 'A002');


-- Inserindo fila associada a serviços e senhas
INSERT INTO fila (pk_fila, fk_servico, fk_senha, prioridade)
SELECT gen_random_uuid(), 
       (SELECT pk_servico FROM servico ORDER BY RANDOM() LIMIT 1), 
       (SELECT pk_senha FROM senha WHERE numero = 'A001'),
       'MEDIA'
WHERE NOT EXISTS (SELECT 1 FROM fila WHERE fk_senha = (SELECT pk_senha FROM senha WHERE numero = 'A001'));

INSERT INTO fila (pk_fila, fk_servico, fk_senha, prioridade)
SELECT gen_random_uuid(), 
       (SELECT pk_servico FROM servico ORDER BY RANDOM() LIMIT 1), 
       (SELECT pk_senha FROM senha WHERE numero = 'A002'),
       'ALTA'
WHERE NOT EXISTS (SELECT 1 FROM fila WHERE fk_senha = (SELECT pk_senha FROM senha WHERE numero = 'A002')); */
