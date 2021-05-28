# RovinePerdute
**Necessita della libreria:**[Utils (versione minima 1.2)](https://github.com/ThatCmd/Utils/releases/tag/1.2)

### Funzionalità presenti
Viene richiesto il percorso alla cartella contenente i file XML. I file sono elencati per dimensione e quando vengono caricati viene controllata la struttura del file che deve seguire la seguente struttura:
  >Un solo elemento "map" che contiene da 1 a N elementi "city" che a loro volta contengono da 0 a N elementi "link"

Il controllo della struttura allunga il tempo di caricamento di un file di qualche secondo. 
<p>Dopo aver caricato il file si può selezionare quale nodo è la partenza e quale la fine: viene proposta un'opzione default (Primo - Ultimo) e un'altra opzione che permette di selezionare i nodi tramite gli ID delle città.</p>
<p>Solo dopo aver superato i passaggi precedenti è possibile procedere alla ricerca del percorso migliore per le due tipologie di veicolo.</p>

### Capacità
La raccolta dei seguenti dati è stata eseguita solamente con il file di 10.000 nodi:<br>
<table>
  <tr>
    <td></td>
    <td>Lettura e caricamento</td>
    <td>Verifica file</td>
    <td>Ricerca algoritmo</td>
    <td>Scrittura output</td>
    <td>Totale</td>
  </tr>
  <tr>
    <td>Min (ms)</td>
    <td>3031</td>
    <td>125</td>
    <td>1922</td>
    <td>93</td>
    <td>5171</td>
  </tr>
  <tr>
    <td>Max (ms)</td>
    <td>3297</td>
    <td>312</td>
    <td>2109</td>
    <td>297</td>
    <td>6015</td>
  </tr>
  <tr>
    <td>Media (ms)</td>
    <td>3178</td>
    <td>215</td>
    <td>2000</td>
    <td>128</td>
    <td>5521</td>
  </tr>
</table>
<p>I tempi sono espressi tutti in millisecondi e sono i tempi totali di esecuzione del processo corrispondente (sia per il team 1 che per il team 2).</p>
<p>Per risparmiare tempo di calcolo le ricerce dei percorsi per i due teams sono separate su due processi differenti in modo da "dimezzare" i tempi di esecuzione.</p>

###Diagramma UML
Il diagramma delle classi non è stato caricato poichè solamente 3 classi sono effettivamente usate nel progetto...
