import os
import re
import datetime

searchdir = os.environ.get("PWD")
allfiles = []
for root, dir,files in os.walk(searchdir):
    for f in files:
        allfiles.append( os.path.join( root,f))
    #files.sort(key=lambda x: os.path.getmtime(os.path.join( root,x))

allfiles.sort(key=lambda x: os.path.getmtime(x))
for f in allfiles:
    print f,datetime.datetime.fromtimestamp(os.path.getmtime(f))
