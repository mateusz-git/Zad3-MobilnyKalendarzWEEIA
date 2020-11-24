# Dokumentacja



## Zadanie 3 - Mobilny Kalendarz WEEIA

### Endpoint
Metoda : GET

Ścieżka : /calendar?year={year}&month={month} 

Parametr : year(typ int) - rok dla którego ma być pobrany plik

Parametr : month(typ int) - miesiąc dla którego ma być pobrany plik
           
Opis :  Zwraca plik z rozszerzeniem .ics, który zawiera wydarzenia z danego miesiąca i roku pobranego ze strony ``http://www.weeia.p.lodz.pl/ ``         

## Przykłady użycia
``
http://localhost:8080/calendar?year=2020&month=10
``

Odpowiedź : Otrzymujemy plik Calendar_weeia.ics dla października 2020 z wydarzeniami i kod odpowiedzi 200

``
http://localhost:8080/calendar?year=2020&month=14
``

Odpowiedź : Otrzymujemy komunikat ,,Month is incorrect,, i kod odpowiedzi 502

``
http://localhost:8080/calendar?year=2222&month=10
``

Odpowiedź : Otrzymujemy komunikat ,,Year is incorrect,, i kod odpowiedzi 502
