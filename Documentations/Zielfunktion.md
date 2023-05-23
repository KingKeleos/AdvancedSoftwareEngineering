# Zielfunktion
1415813

## Herleitung

------

Bei dem Green Vehicle Problem wird ein Satz an Depots, Customers, sowie ein Satz an Tankstellen vorausgesetzt. \
Man geht dabei davon aus, dass die Tankstellen unendlich viele Fahrzeuge versorgen kann, sowie es unendlich Autos am Depot gibt.

Dabei sind die Parameter für die Autos, sowie den Orten folgende:
- 1 Depot (1), 10 Tankstellen (F), 20 Kunden (C)
- Depots unendlich Fahrzeuge
- Autos besitzen maximales Volumen von 60 Gallonen
- Verbrauch liegt bei 0,2 Gallonen pro Meile $\frac{0,2 \text{Gallonen}}{1 \text{Meile}}$
- Maximale Durchschnittsgeschwindigkeit liegt bei $\frac{40 \text{Meilen}}{h}$
- Konstante Verweil-/Servicedauer : $30 min$
- konstante Dauer je Tankvorgang : $15min$
- maximale Dauer bis alle Touren abgeschlossen sein müssen : $10h45min$

Die Autos sollen dabei jeden Kunden einmalig besuchen.

Somit müssen die Fahrzeuge mit der konstanten Geschwindigkeit, die sie haben, alle Orte innerhalb der Zeit von $10h45min$ besucht haben.

Ebenso müssen jedoch die Fahrzeuge ihren Tank beachten, bevor sie ihr nächstes Ziel wählen, damit diese nicht "auf der Strecke bleiben".

Die Berechnung für die Zeit ist dabei: $t(ij)=\frac{c(ij)}{v}$, dabei ist $c(ij)$ die Distanz zwischen den Städten i und j. 

## Pseudoalgorithmus
---
Mithilfe der oben gegebenen Funktionen lässt sich folgender Pseudoalgorithmus erstellen:

``` bash
Fahrzeuge Initialisieren
Fahrzeuge starten alle an Ort D
Orte initialisieren
for Fahrzeug f
    while Zeit t <= 10h45min und Nicht alle Customer besucht do
            Nächsten Customer c auswählen
            Distanz s berechnen
            if Tank > (Verbrauch * Distanz)
                then Ziel wählen
                    Tank von Fahrzeug f aktualisieren
                    Zeit t + Distanz / Geschwindigkeit + Verweildauer Kunde
                    Customer Position wird aktuelle Position
                    Customer c aus Liste entfernen
                else
                    nächste Tankstelle suchen
                    Zu Tankstellen fahren
                    Zeit t + Distanz / Geschwindigkeit + Tankvorgang
                    Tankstelle wird aktueller Ort
            end if
    end while
end for
```

Mithilfe dieses Pseudoalgorithmus kann nun die Entwicklung des Algorithmus starten.