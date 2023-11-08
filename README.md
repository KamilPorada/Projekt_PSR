# Projekt_PSR
Projekt został wykonany w ramach zajęć projektowych Programowanie systemów rozproszonych na Politechnice Świętokrzyskiej
Temat:
Implementacja rozproszonego narzędzia do obliczania wartości całek oznaczonych metodami numerycznymi.

Opis projektu:
Narzędzie będzie miało za zadanie obliczać wartości całek oznaczonych w zależności od zadanych parametrów:
poziomu złożoności obliczeniowej,
liczby podziałów (dotyczy metod iteracyjnych), długości tablicy całkowej (dotyczy metod rekurencyjnych),
przedziału całkowania.

Zostaną zaprezentowanie następujące algorytmy całkowania numerycznego:
metoda prostokątów,
metoda trapezów,
metoda parabol (Simpsona),
metoda Romberga.

Główny zarządca będzie odbierał dane od użytkownika dotyczące wyżej wymienionych parametrów,
a następnie będzie je wysyłał do klientów. Klienci będą dokonywać niezbędnych obliczeń numerycznych 
oraz będą zwracali wynik w określonym czasie, oraz z pewną dokładnością. Po zwróceniu danych zarządca 
będzie miał za zadanie dokonać analizy wyników: tj. dokładność przybliżeń metodami numerycznymi, 
długość trwania obliczeń itd. We wnioskach będzie dokonywał wyboru, który z algorytmów będzie
w tym przypadku najlepszym rozwiązaniem.
