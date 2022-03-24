CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt TEXT,
  password TEXT,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteId INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

DROP TABLE IF EXISTS FILES;
CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filesizeinbytes LONG,
    filename TEXT NOT NULL,
    filetype TEXT NOT NULL,
    filedata BLOB
);
--     contenttype TEXT NOT NULL, REMOVED because of H2 continues error
-- foreign key (userid) references USERS(userid)

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialId INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT
);
--     foreign key (userid) references USERS(userid)