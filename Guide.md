# Anleitung zum Schreiben von Verfahren-Plugins

## Einführung

Das Schreiben von Plugins ist prinzipiell nicht schwer. Dennoch werden Java-Grundkentnisse benötigt. Die Schnittstelle für Plugins ist möglichst offen gehalten. Das bedeutet konkret, dass auch Verfahren, welche inhaltlich weit entfernt von unseren Standard-Verfahren sind, umgesetzt werden können. Um die Arbeit zu Verringern, bieten wir eine große Anzahl an vorgefertigten Bausteinen, sogenannte PluginUtils an, welche in dem Package kodex.pluginutils.* zu finden sind.
Die Umsetzung von Verfahren berücksichtigt das Model-View-Presenter Entwurfsmuster, welches in seinen Grundzügen eingehalten werden muss. Eine kurze und für unseren Zweck ausreichende Einführung in das Modell ist unter https://de.wikipedia.org/wiki/Model_View_Presenter zu finden. Mehr Kentnisse darüber werden nicht benötigt. Unsere Plugins stellen eine Orientierung dar, wie eigene Plugins umgesetzt werden können. Ein Blick in diese, kann sich also durchaus lohnen.
Aus Gründen der Übersicht, ist es sinnvoll, direkt eine Namen für das Plugin zu wählen, welcher in den fortlaufenden Unterpunkten beibehalten wird, und auch die angegebene Struktur zu erfüllen.

## Paket-Struktur

 1. *verfahrensname*
 2.  *verfahrensname*.presenter
 3.  *verfahrensname*.presenter.chainlink
 4. *verfahrensname*.presenter.edit
 5. *verfahrensname*.presenter.header
 6. *verfahrensname*.model.steps
 7. *verfahrensname*.model.content

## Import von Dateien
Hierfür wird eine Klasse benötigt. Diese soll den Namen *Verfahrensname*ImportPresenter tragen und in Paket 2, dem *verfahrensname*.presenter Paket, liegen. Diese Klasse erweitert den ImportPresenter. Das bedeutet konkret für ein Plugin mit dem Namen "ColorImage"

```java
    public class ColorImageImportPresenter extends ImportPresenter { ... }
```

Diese Klasse benötigt einen Konstruktor, welcher wie folgt aussieht:

```java
    public ColorImageImportPresenter(ProcedurePlugin plugin) {
	     super(plugin);
	}
```

Außerdem muss die Methode getView() : AnchorPane überschrieben werden.  Diese soll eine AnchorPane zurück geben, welche ein Menü zum Importieren von Dateien ermöglicht. FXML Dateien bieten sich hierfür an. Ein Beispiel für "Encoding" und "Decoding" lässt sich unter src/main/resources/standardplugins/colorimageprocedure/presenter finden.

Es werden nun die Methoden für die Behandlung von Klicks auf "Encoding" und "Decoding" benötigt. Dafür müssen die Methoden "handleDecodeImport" und "handelEncodeImport" überschrieben werden. Diese Methoden haben die Aufgabe einen Inhalt zu laden (z.B. dynamisch über den FileChooser) und müssen diesen validieren (Abfrage auf Gültigkeit empfehlenswert). Für die Validierung müssen die Methoden "validateEncodeImport" und "validateDecodeImport" überschrieben werden. Diese geben einen Boolean zurück, welcher genau dann wahr sein soll, wenn der Import gültig ist.

Es ist außerdem noch möglich private Methoden in dieser Klasse zu implementieren, um weitere Möglichkeiten, wie z.B. den Sprachwechsel oder das Auslesen einer Textdatei, zu ermöglicht. Beispiele für solche Methoden sind unter kodex.standardplugins.colorimageprocedure.presenter.ColorImagePresenter zu finden.


## Informationen über das Plugin
Hierfür wird eine Klasse benötigt. Diese soll den Namen *Verfahrensname*ProcedureInformation tragen und in Paket 1, dem *verfahrensname* Paket, liegen. Diese Klasse erweitert die Klasse ProcedureInformation. Das bedeutet konkret für ein Plugin mit dem Namen "ColorImage"

```java
    public class ColorImageProcedureInformation extends ProcedureInformation { ... }
```

In dem Konstruktor soll ein Symbol `this.icon = new Image(Pfad zum Bild)`, eine Beschreibung `this.description = "Kurzbeschreibung"` und die Labels gesetzt werden. Die Labels sollten als ArrayList gespeichert werden. Falls das Plugin für schulische Zwecke geeignet sein soll, sollte an erster Stelle die Klassenstufe stehen, ab wann dieses Plugin geeignet ist. Die Position ist für die Sortier-Funktion nach Klassenstufe wichtig. An zweiter Stelle sollte angegeben werden, um welchen Verfahrenstyp es sich handelt und an dritter Stelle, welche Möglichkeiten dieses Plugin bietet.  Weitere Felder können frei gewählt werden. 
Ein Beispiel für konkrete Labels des Farbbild-Verfahrens wäre:
 `this.labels = FXCollections.observableArrayList("7", "Kodierungsverfahren", "Kodieren und Dekodieren")`
Außerdem muss diese Klasse die Methode getName() : String überschreiben, welche einen String, mit dem Namen des Plugins zurück gibt:

```java
    @Override
    public String getName() {
	    return "Name des Verfahrens";
	}
```
	
Die Informationen über das Plugin sind nun vollständig implementiert.

## Erstellen des Plugins

Hierfür wird eine Klasse benötigt. Diese soll den Namen *Verfahrensname*ProcedurePlugin tragen und in Paket 1, dem *verfahrensname* Paket, liegen. Das ProcedurePlugin bietet den Zugangspunkt des Plugins für das Framework. Diese Klasse erweitert die Klasse ProcedurePlugin. Das bedeutet konkret für ein Plugin mit dem Namen "ColorImage"

```java
    public class ColorImageProcedurePlugin extends ProcedurePlugin { ... }
```

Folgende Methoden müssen nun überschrieben werden:
- createImportPresenter (stellt die Klasse, welche für den Import zuständig ist, bereit) (siehe Punkt: Import von Dateien)
- createProcedureInformation  (stellt die Klasse, welche für die Informationen zuständig ist, bereit) 
	(siehe Punkt: Informationen über das Plugin)
- pluginDescriptionProperty (gibt Beschreibung des Plugins zurück)
- pluginNameProperty (gibt Name des Plugins zurück)

Als Orientierung, wie solch eine Klasse aussieht, folgende abstrakte Implementierung:

> Bitte Beachten: Die Schritte zwischen den einzelnen Stufen wurden bisher nicht implementiert. 
> Dies erfolgt im nächsten Schritt.

```java
    public class KonkretesProcedurePlugin extends ProcedurePlugin {
    
     /* Schritte der Kodierungskette */
     private ChainLinkPresenter[] chainLinks; // mindestens zwei Schritte benötigt
     
     /** Konstruktor der Klasse */
     public KonkretesProcedurePlugin() {
     }
      
     /**
	  * Initalisierung der Schritte
      */
    public void initializeProcedure() {
	     chainLinks = new ChainLinkPresenter[Anzahl der Schritte]; 
	     Stufe0zu1 schritt0 = new Stufe0zu1();
	     Stufe1zu2 schritt1 = new Stufe1zu2();
	     Stufe2zu3 schritt2 = new Stufe2zu3();
	     chainLinks[0] = new Stufe0ChainLinkPresenter(null, null, schritt0);
	     chainLinks[1] = new Stufe1ChainLinkPresenter(chainLinks[0], schritt0, schritt1);
	     chainLinks[2] = new Stufe2ChainLinkPresenter(chainLinks[1], schritt1, schritt2);
	     chainLinks[3] = new Stufe3ChainLinkPresenter(chainLinks[2], schritt2, null);
	     
	     //Setzt alle ChainLinks
	     for (int i = 0; i < chainLinks.length - 1; i++) {
		     chainLinks[i].setNext(chainLinks[i + 1]);
	     }
     }
      
     @Override
     public ImportPresenter createImportPresenter() {
	     return new KonkreterImportPresenter(this);
     }
     
     @Override
     public ProcedureInformation createProcedureInformation() {
	     return new KonkreteProcedureInformation();
     }
    
     @Override
     public StringProperty pluginDescriptionProperty() {
	     return new SimpleStringProperty("Kodierungsverfahren"); //Beispiel, Text beliebig
     }
     
     @Override
     public StringProperty pluginNameProperty() {
	     return new SimpleStringProperty("Farbbild");	//Beispiel, Text beliebig
     }
    }
```

Nun kann das Plugin geladen werden. Jedoch hat es keinerlei Funktion. Diese muss nun implementiert werden.

## Übersicht
In den Nachfolgenden Schritten wird die Logik des Plugins implementiert. Grundlegend sollten sich folgende Gedanken gemacht werden:

 1. Welche Stufen werden benötigt?
 2. Wie kommt man von einer Stufe in die vorherige?
 3. Ist das Bearbeiten von Werten möglich?
 4. Ist das Exportieren möglich?
 5. Wie werden Stufen angezeigt?

## Logik des Plugins

> Schon gewusst? Unsere PluginUtils stellen einige Modelle und abstrakte Klassen bereit, welche eine Orientierung bieten, wie ein Modell implementiert wird. Bilder, Matrizen und Texte können meistens direkt durch unsere Klassen verwendet werden oder durch Überschreiben von Methoden nach eigenen Wünschen angepasst werden. Außerdem sind auch einige Schritte zwischen zwei Stufen vorhanden.

**Content:**

Diese Klassen stellen das tatsächliche Objekt, welches für die Stufen relevant sind, bereit. Das Modell aller Stufen sollte in dem Paket "*verfahrensname*.model.content" liegen. Jede einzelne Klasse muss den parameterisierten Content erweitern. Für ein Farbbild sieht dies beispielsweise folgendermaßen aus:

```java
    public class ColorImage extends Content<WritableImage> { ... }
```

Jede Klasse muss einen Konstruktor besitzen. Außerdem müssen die Methoden isValid(): Boolean und export() überschrieben werden. Die Methode isValid() wird aufgerufen, sobald der Nutzer eine Datei importiert und soll den Inhalt auf Gültigkeit überprüfen. Diese Methode soll "true" zurück geben, sofern der Inhalt gültig ist, ansonsten "false". Die Methode export(File file) hat als Parameter einen Pfad, wohin die Datei gespeichert werden soll. Dieser sollte auf Gültigkeit überprüft werden und die Datei in dieses Verzeichnis schreiben. Wie die Datei aus sieht, ist abhängig von dem Inhalt der Stufe. Falls ein Export nicht möglich sein soll, ist es ratsam, einfach eine Warnmeldung auszugeben. Mehr zu Warnmeldungen und dem Sprachwechsel unter dem Kapitel "Extras".

> Bitte beachten: Gegebenenfalls ist ein Header in dieser Datei nötig, um diese wieder Dekodieren zu können

Sind alle Modelle für dieses Verfahren erstellt, kann es mit den Schritten weiter gehen.

**Steps:**

Diese Klassen ist für den Schritt zwischen zwei Content-Klassen notwendig. Die Implementierung aller Schritte sollte in dem Paket "*verfahrensname*.model.steps" liegen. Jede einzelne Klasse muss das Interface "ChainStep" implementieren. Für den Schritt zwischen einem Farbbild und einer RGB-Matrix sieht dies beispielsweise folgendermaßen aus:

```java
    public class ColorImageToRGBMatrix implements ChainStep { ... }
```

Jede einzelne Klasse aus diesem Paket hat in der Regel nur zwei Klassen, welche überschrieben werden müssen:

 1. decode(Content<?> right, Content<?> left) { ... }
 2. encode(Content<?> left, Content<?> right) { ... }

Diese Methoden haben die Aufgabe, eine Umrechnung von einer Stufe in eine andere zu erledigen. Dabei wird ein Content übergeben, welcher die aktuellen Daten besitzt und ein Content, welcher die umgerechneten Daten erhalten soll. Intuitiv geht die Kodierungskette immer von links nach rechts, weshalb die Reihenfolge der Parameter bei decode und encode vertauscht wurde. 

> Exceptions aus dieser Klasse kommen meistens daher, dass die Parameter  in ihrer Reihenfolge vertauscht wurden

Die Logik des Plugins ist nun fertig. Der nächste und letzte Schritt wäre die Anzeige und Interaktion innerhalb des Verfahrens.

## Anzeige und Interaktionen der Stufe
> Schon gewusst? Auch hier haben unsere PluginUtils einige Funktionen bereits implementiert, welche gegebenenfalls benutzt werden können

Für jede Stufe sind bis zu drei Presenter notwendig:

 1. ein Presenter für die Stufe
 2. ein Presenter für den Header
 3. ein Presenter für die Bearbeiten-Funktionalität

Punkt 1 muss implementiert werden, Punkt 2 und 3 sind optional, können jedoch für das Verständnis des Verfahrens helfen.

**ChainLinkPresenter:**

Diese Klasse soll den Namen *ContentName*Presenter tragen und in Paket 3, dem *verfahrensname*.presenter.chainlink Paket, liegen. Diese Klasse erweitert den ChainLinkPresenter. Das bedeutet konkret für einen Content mit dem Namen "ColorImage"

```java
    public class ColorImagePresenter extends ChainLinkPresenter { ... }
```

Der Konstruktor soll dabei (falls implementiert) den Content, den Presenter für den Header und den Presenter für die Bearbeiten-Funktionalität setzen. 
Konkret sieht der Konstruktor folgendermaßen aus:

```java
    public ColorImagePresenter(ChainLinkPresenter prev, ChainStep prevStep, ChainStep nextStep) {
	    super(prev, prevStep, nextStep);
	    content = new ColorImage();
	    chainLinkEditPresenter = new ColorImageEditPresenter(this); //optional, siehe nächsten Punkt
	    chainLinkHeaderPresenter = new ColorImageHeaderPresenter(this); //optional, siehe nächsten Punkt 
	}
```

Als nächstes wird die Anzeige des Models benötigt. Dazu muss die Methode getView() : AnchorPane überschrieben werden.  Diese Methode soll den aktuellen Content innerhalb einer AnchorPane zurück geben.
Wenn eine Stufe minimiert wird, soll der Name der Stufe vertikal angezeigt werden. Dafür muss nur der Name der Stufe angegeben werden, der Rest wird durch unser Framework erledigt. Dazu muss die Methode getName() : String überschrieben werden. Diese sieht beispielsweise folgendermaßen aus:

```java
    @Override
    public String getName() {
	    return "Farbbild";
	}
```


 **ChainLinkHeaderPresenter:**

Diese Klasse soll den Namen *ContentName*HeaderPresenter tragen und in Paket 5, dem *verfahrensname*.presenter.header Paket, liegen. Diese Klasse erweitert den ChainLinkHeaderPresenter. Das bedeutet konkret für einen Content mit dem Namen "ColorImage"

```java
    public class ColorImageHeaderPresenter extends ChainLinkHeaderPresenter { ... }
```

Diese Klasse benötigt einen Konstruktor, welcher folgendermaßen aufgebaut ist:

```java
    public ColorImageHeaderPresenter(Content<?> content) {
	    super(content);
	}
```

Als nächstes wird die Anzeige des Headers benötigt. Dazu muss die Methode getView() : AnchorPane überschrieben werden.  Diese Methode soll den aktuellen Header innerhalb einer AnchorPane zurück geben.
Es bietet sich an, alle benötigten Header-Dateien direkt in den Content zu schreiben und diese lediglich in dieser Methode auszugeben. Natürlich kann auch eine eigene Implementierung sinnvoll sein.
Eine Implementierung für die getView() : AnchorPane könnte beispielsweise folgendermaßen aussehen:

```java
    @Override
    public AnchorPane getView() {
	    Map<String, Object> map = content.getheader();
	    TextFlow headerTextFlow = new TextFlow();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Text headerEntry = new Text(entry.getKey() + ": " + entry.getvalue() + "\n");
			headerTextFlow.getChildren().add(headerEntry);
		}
		return new AnchorPane(headerTextFlow);
	}
```

Weitere Meta-Informationen können natürlich einfach als Text hinzugefügt werden oder durch eine neue Implementierung an die jeweilige Stufe, nach eigenen Wünschen, angepasst werden.

**ChainLinkEditPresenter:**

Diese Klasse soll den Namen *ContentName*EditPresenter tragen und in Paket 4, dem *verfahrensname*.presenter.editPaket, liegen. Diese Klasse erweitert den ChainLinkEditPresenter. Das bedeutet konkret für einen Content mit dem Namen "ColorImage"

```java
    public class ColorImageEditPresenter extends ChainLinkEditPresenter { ... }
```

Diese Klasse benötigt einen Konstruktor, welcher folgendermaßen aufgebaut ist:

```java
	private ColorImage content;

    public ColorImageHeaderPresenter(ChainLinkPresenter chainLinkPresenter) {
	    super(chainLinkPresenter);
		... 
		//Es ist sinnvoll alle Eingabefelder für die Bearbeiten-Funktion zu initalisieren.
		//Auch kann hier bereits geprüft werden, ob die Eingabe überhaupt möglich ist.
		//Hierzu bietet sich ein UnaryOperator an, welcher das Eingabefeld überprüft.
		//Die View kann auch bereits hier gesetzt werden.

		content = (ColorImage) chainLinkPresenter.getContent();
	}
```

Als nächstes wird die Anzeige des Inhaltes, welcher bearbeitet werden soll, benötigt. Dazu muss die Klasse getView(): AnchorPane überschrieben werden. In den meisten Fällen bietet es sich an, nur einen kleinen Abschnitt (bspw. ein Byte oder einen einzelnen Pixel) anzuzeigen. 
Zum Berechnen dieses Abschnittes ist die Methode updateMarkedElement() gedacht. Ein typischer Anwendungsfall wäre also, dass getView() die Methode updateMarkedElement() aufruft, diese Methode den Content in einem privaten Attribut view :  AnchorPane setzt und getView() dieses Attribut zurück gibt. Natürlich sind aber auch weitere Berechnungen oder zusätzliche Anzeigen in dieser Methode möglich.

Nun muss der Angezeigte Inhalt der Stufe noch aktualisiert werden. Dazu muss die Methode handleSubmit() überschrieben werden. Das zu bearbeitende "Content"-Objekt wurde bereits im Konstruktor initialisiert. Die Methode sollte folgendermaßen aufgebaut werden:

```java
    @Override
    public void handleSubmit() {
	    //Nutzereingabe aus dem Eingabefeld auslesen
	    //Nutzereingabe auf Gültigkeit überprüfen
	    //content anpassen 
	    chainLinkPresenter.updateChain();
	}
```

Die Implementierung des Plugins ist nun abgeschlossen.  In dem nächsten Kapitel wird erklärt, wie Fehlermeldungen ausgegeben werden und wie das Plugin in mehreren Sprachen angeboten werden kann. Natürlich ist dies nur optional.

## Extras

**Fehlermeldungen und Warnhinweise:**

Fehlermeldungen und Warnhinweise können zum besseren Verständnis des Nutzers helfen. Das Framework bietet eine einfache Möglichkeit, diese auszugeben.
Grundlegend sollten zwischen Warnungen und Fehlern unterschieden werden. Warnungen sollen die aktuelle Interaktion nicht beenden, sondern gegebenenfalls nur in ihrer Wirkung einschränken.
Grundlegend können Warnungen und Fehlermeldungen über eine Exception ausgegeben werden.

Für eine Fehlermeldung sieht dies folgendermaßen aus:

```java
    throw new KonkreteException(AlertType.ERROR, 
	    "Titel des Fensters", "Fehlername", "Beschreibung des Fehlers");
```

Eine Warnung kann wie folgt ausgegeben werden:

```java
    throw new KonkreteException(AlertType.WARNING, 
	    "Titel des Fensters", "Name der Warnung", "Beschreibung der Warnung");
```

**Übersetzungen:**

Unser Framework bietet eine komfortable Möglichkeit der Übersetzung. Dabei wird auf Bindings gesetzt. Konkret wird für jedes Element, welches übersetzt werden kann, ein Binding erstellt, welches in der entsprechenden Property-Datei nach diesem Namen sucht und den gewünschten Text ausgibt. Ist der Eintrag nicht vorhanden, wird automatisch Englisch als Standardsprache gewählt.

> Da die Sprachdateien intern liegen, sollte ein Plugin nicht in diese schreiben. Das bedeutet, dass nur diejenigen Texte übersezetzt werden können, welche bereits in der Property-Datei vorhanden sind. Ein Sprachwechsel kann so beispielsweise das Import-Fenster übersetzen

Die Initialisierung des Import-Presenters sieht beispielsweise folgendermaßen aus:

```java
    @FXML
    private Button encodeImportButton;

	@FXML
    private Button encodeImportButton;
 
    @FXML
    private void initialize() {
		 encodeImportButton.textProperty()
			 .bind(I18N.createStringBinding("importexample.encode.importbutton"));
		 decodeImportButton.textProperty()
			 .bind(I18N.createStringBinding("importexample.decode.importbutton"));
	}
```

Die Felder "importexample.encode.importbutton" und "importexample.decode.importbutton" sollten in der Property-Datei vorhanden sein. Ein Eintrag in dieser sieht beispielsweise folgendermaßen aus:

```
    importexample.decode.importbutton=Datei zum Dekodieren auswählen
    importexample.encode.importbutton=Datei zum Kodieren auswählen
```

Wird lediglich die Rückgabe eines Strings benötigt, so erfolgt dies folgendermaßen:
	
```java
    I18N.get(String): String
```

  Zum Beispiel: `String text = I18N.get("importexample.decode.importbutton");`

```java
    System.out.println(text);
```
    > Datei zum Dekodieren auswählen //falls aktuelle Sprache DE

  

## Fertigstellung
Bevor man das Plugin exportiert benötigt man noch einen src/main/resources/META-INF/services Ordner. Hier kommen Dateien rein, die dem ServiceLoader sagen, welche Interfaces und abstrakten Klassen die Klassen implementieren.
Dafür nennt man die Dateien so wie den vollqualifizierten Namen des Interfaces oder der abstrakten Klasse (z.B. kodex.plugininterface.Plugin). Achtung: Es soll keine weitere Dateiendung hinzugefügt werden!
Die Datei öffnet man dann mit einem Texteditor und trägt den vollqualifizierten Namen der implementierenden Klassen ein (z.B. kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin).
Da ProcedurePlugin von Plugin erbt, soll eine Implementation von ProcedurePlugin in beide Dateien eingetragen werden. Somit werden die ProcedurePlugins als Plugin und ProcedurePlugin geladen.

Das neue Verfahren ist nun vollständig. Es sollte noch einmal auf Warnungen und Fehler in der IDE überprüft werden. 
Nun muss das Plugin nur noch als .jar Datei exportiert werden. Dies kann, falls während der Entwicklung Maven (https://maven.apache.org) benutzt wurde, mittels `clean install -U` erfolgen oder direkt über die Exportieren-Funktion der IDE.
Ist die .jar Datei erstellt, lässt sich diese ganz einfach über das Plugin-Menü innerhalb von KodeX importieren und taucht, nach der Aktivierung, auf der Startseite als neues Verfahren auf.