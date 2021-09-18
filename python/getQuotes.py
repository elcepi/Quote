#!/usr/bin/python3

import urllib3
from bs4 import BeautifulSoup
import sys
import re

selector = {
	"www.goodreads.com": [".quoteText"],
	"www.brainyquote.com": [".b-qt"],
	"www.santabanta.com": [".display_block td"],
	"www.keepinspiring.me": [".wp-block-quote"],
	"www.morefamousquotes.com": [".quote > p > a", ".quote > script+p > :not(a)"],
	"bayart.org": [".wp-block-quote"],
	"citatis.com": ["blockquote"],
	"quotestats.com": ["blockquote"],
}

class GetQuotes:
	def __init__(self):
		host = urllib3.get_host(sys.argv[1])[1]
		self.cssSel = selector[host]
		self.soup = BeautifulSoup(urllib3.PoolManager().request("GET", sys.argv[1]).data.decode("utf-8"), "html.parser")
		super().__init__()

	def process(self):
		for s in self.cssSel:
			for a in self.soup.select(s):
				line  = a.get_text()
				if line:
					line = re.sub("\n+","\n",line)
					line = re.sub("–.*|”|“|#[0-9]+\\.\\s","",line)
					line = re.sub("^\s+", "", line)
					line = re.sub("\s+$", "", line)
					print(line)
					print("--")

GetQuotes().process()

