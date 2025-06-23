create table category(
        id int auto_increment primary key,
        name varchar(50) not null
);

create table question(
        id varchar(50) PRIMARY key,
        content text not null,
        category_id int not null,
        index fk_question_category_idx (category_id asc) visible,
        constraint fk_question_category
        foreign key (category_id) REFERENCES category(id)
);

create table choice(
    id varchar(50) primary key,
    content varchar(255) null,
    is_correct bit null,
    question_id varchar(50) null,
    index fk_choice_question_idx (question_id asc) visible,
    constraint fk_choice_question foreign key (question_id) REFERENCES question(id)
);
select * from englishdb.question;
select * from englishdb.choice;
select * from englishdb.category;
insert into englishdb.category(name) values ('Noun'), ('Verb'), ('Adjective'), ('Adverb');