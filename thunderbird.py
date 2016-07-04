import sqlite3 as sql
import sys
# get query as input
input= sys.argv[1] 
con = sql.connect('/home/subh/.thunderbird/ip12lpd5.default/global-messages-db.sqlite')

cur=con.cursor()
#getting the stemmer's hex code
cur.execute("select hex(fts3_tokenizer('porter'))")

row = cur.fetchone()
#Here , we are setting the fast text tokenizer to the previously mentioned  code.
s = "select fts3_tokenizer('mozporter',X'"+ row[0]+"')"
print s
cur.execute(s)
cur.fetchone()
#Print all emails where either body , subject , attachmentNames , author , recipients  contain python
#cur.execute("select * from messagesText where messagesText MATCH 'python'")
cur.execute(input)
for row in cur:
    print row
    n = raw_input('press n to continue: ')
    if n is 'N' or n is 'n':
        continue
    else:
        sys.exit(0)

