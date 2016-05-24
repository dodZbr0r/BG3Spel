# BG3Spel
Spelidé:

Sidescrolling
Skjuter iväg ett objekt (boll, flygplan)
objektet ska ta sig 


Uppdelning:
Graphics: Daniel, Henrik, Victor
Fysikmotor, Fysikberäkningar: Adrian, David, Linnea, Erik

----------------------------------------------------------------------------------------------------
Möte 1 2016-05-10

Brainstorm för spelgrunden:

NIVÅ 1:
- fast fönster
- boll med massa och eventuell elasticitet
- Kraft och vinkel för att få iväg boll
- gravitation

NIVÅ 2:
- Kollision med marken
- Perfekt Elastisk stöt
- Fejk luftmotsånd
- Försök få till mindre studs
- Försök få till mer realistisk friktion

NIVÅ 3:
- Scrollande bakgrund (stor bakgrund)
- Bollrelativt fönster
- Generera bakgrund (mer mark)

NIVÅ 4:
- Införa startfjäder
- Införa studsobjekt
- Randomgenerera

----------------------------------------------------------------------------------------------------
Möte 2 2016-05-11

Fönsterstorlek bestämt till 1280x720 pixlar.
Läng bestämt till 100 pixlar = 1 meter
Koordinater ska skrivas i mm, d.v.s. 1000 = 1 m
Radie på boll bestämt till 500.
Standard för vinklar bestäms til grader

----------------------------------------------------------------------------------------------------
Möte n 2016-05-24

Möjliga fixar/extra saker/DLC:

- Få grafiken att veta hur ofta den uppdaterar så att bakgrunden och marken ändras ordentligt
  (Overridea repaint och skicka med uppdateringstiden för grafik)
  Alternativt ha med bakgrund och mark i Game, och skicka in hur mycket dem ska ändras till GameComponent

-
