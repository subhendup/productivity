import groovy.sql.Sql
//ensure sqlite-jdbc-.jar in classpath or $GROOVY_HOME/lib
def sql = Sql.newInstance("jdbc:sqlite:/home/subh/.thunderbird/ip12lpd5.default/global-messages-db.sqlite", "", "", "org.sqlite.JDBC")
//getting the stemmer's hex code
def row=sql.firstRow("select hex(fts3_tokenizer('porter')) as val")
println row.val
//Here , we are setting the fast text tokenizer to the previously mentioned  code.
def s = "select fts3_tokenizer('mozporter',X'"+ row.val+"')"
row = sql.firstRow(s)
//Print all emails where either body , subject , attachmentNames , author , recipients  contain python
sql.eachRow("select * from messagesText where messagesText MATCH 'python'"){
    println it
}


/*
sql.eachRow('select * from conversations'){
    println it
} 
*/
