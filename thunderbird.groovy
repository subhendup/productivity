import groovy.sql.Sql
def sql = Sql.newInstance("jdbc:sqlite:/home/subh/.thunderbird/ip12lpd5.default/global-messages-db.sqlite", "", "", "org.sqlite.JDBC")
def row=sql.firstRow("select hex(fts3_tokenizer('porter')) as val")
println row.val
def s = "select fts3_tokenizer('mozporter',X'"+ row.val+"')"
row = sql.firstRow(s)
sql.eachRow("select * from messagesText where messagesText MATCH 'python'"){
    println it
}


/*
sql.eachRow('select * from conversations'){
    println it
} 
*/