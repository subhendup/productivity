import sqlite3 as sql
import sys
input= sys.argv[1]
con = sql.connect('/home/subh/.thunderbird/ip12lpd5.default/global-messages-db.sqlite')

cur=con.cursor()
cur.execute("select hex(fts3_tokenizer('porter'))")

row = cur.fetchone()

s = "select fts3_tokenizer('mozporter',X'"+ row[0]+"')"
print s
cur.execute(s)
cur.fetchone()
#cur.execute("select * from messagesText where messagesText MATCH 'python'")
cur.execute(input)
for row in cur:
    print row
    n = raw_input('press n to continue: ')
    if n is 'N' or n is 'n':
        continue
    else:
        sys.exit(0)

