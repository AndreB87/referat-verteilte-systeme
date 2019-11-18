Vorraussetzungen:
- Python3 muss installiert sein

Configurationen:
- in der Datei 'conf.json' muessen Hostadresse und Port angegeben werden
	- "HOST": hier muss die Hostadresse innerhalb der "" Zeichen 
          angegeben werden, das Komma am Ende der Zeile muss vorhanden sein
		- Beispiel: "HOST" : "192.168.1.111",
		- Wird 0.0.0.0 angegeben, wird auf allen IP-Adressen 
                  gehorcht
	- "PORT": Hier wird der Port, auf dem gehorcht wird, angegeben. 
          Hier wird kein Komma am Ende der Zeile benoetigt
		- Beispiel: "PORT": 42

Starten:
- python[3] startNameService.py
