[![SAM Deploy](https://github.com/HauVu94/PGR301-DevOps-Eksamen/actions/workflows/deply.yml/badge.svg)](https://github.com/HauVu94/PGR301-DevOps-Eksamen/actions/workflows/deply.yml)


## Her har jeg laget en check liste for meg selv. Besvarelse for oppgaven kommer etter checklisten

# Eksamens oppgaver sjekkliste

## Oppgave 1 Kjell's Python kode - 20 Poeng
- [x] Oppgave A - SAM & GitHub actions workflow
- [x] Oppgave B - Docker container

## Oppgave 2 Overgang fra Java og Spring boot - 15 poeng
- [x] Oppgave A - Dockerfile
- [x] Oppgave B - GitHub Action workflow for container image og ECR

## Oppgave 3 - Terraform, AWS Apprunner og Infrastruktur som kode - 15 poeng
- [ ] Oppgave A - Kodeendringer og forbedringer
- [ ] Oppgave B - Terraform i GitHub Actions

## Oppgave 4 - Feedback - 30 poeng
- [ ] Oppgave A - Utvid applikasjonen og legg inn "Måleinstrumenter"
- [ ] Oppgave B - CloudWatch Alarm og Terraform moduler

## Oppgave 5 - Drøfteoppgaver - 20 poeng
- [x] Oppgave A - Kontinuelig Integrering

- [x] Oppgave B - Sammenligning av Scrum/Smidig og DevOps fra et Utviklers Perspektiv 
    - [x] Oppgave 1 - Scrum/Smidig Metodikk
    - [x] Oppgave 2 - DevOps Metodikk
    - [x] Oppgave 3 - Sammenligning og Kontrast
  
- [x] Oppgave C - Det Andre Prinsippet Feedback 


# Besvarelse oppgave 1

Først må du/sensor Forke repositoryen.

Hvis du går inn på din terminal og skriver inn: git clone https://github.com/<DIN-GITHUB-BRUKER>/PGR301-DevOps-Eksamen.git

Etter at du har fått forket er det viktig å lage Github Secrets i repositiriet.

    1. gå inn på settings
    2. under Security finner du Secrets and variables, gå til Actions
    3. Her må du lage 2 secrets, den ene kaller du for AWS_ACESS_KEY_ID og den andre AWS_SECRET_ACESS_KEY.
        Du kan finne disse kodene inne på AWS og IAM, da vil du knytte AWS kontoen din til Github
    4. Du trenger å gjøre en endring i '.github/workflows/deply.yml' for å kunne pushe opp til github.
    5. i terminal i rotmappen kan du skrive "git add .", deretter 'git commit -m "skriv en commit melding" ', deretter git push. 
    6. Github Actions vil da kjøre, hvis alt funker vil den kjøre grønt :)
    
    

    
    

# Besvarelse oppgave 5

## Oppgave A

Kontinuerlig integrasjon (CI) kan kort forklares som følger: 

Når vi er et team på for eksempel fem personer og alle jobber i samme repository, men har hver våre egne branches, 
vil koden automatisk integreres i repositoriet når vi pusher endringer, og dette kan skje flere ganger om dagen. 

Når vi for eksempel pusher til Github, blir koden umiddelbart sjekket for godkjenning eller feil. Hvis det oppdages feil, kan vi umiddelbart rette dem. 
CI kjører automatisk bygg og tester hver gang det er endringer i koden.

Ved å oppdage og løse problemer tidlig reduserer CI tiden det tar å utvikle og implementere ny funksjonalitet. 
Dette muliggjør raskere levering av programvare. Når vi jobber med CI, kan hver enkelt utvikler fokusere på sitt eget arbeid uten å bekymre seg for andres, 
da CI sørger for at alt skal gå knirkefritt.


Hvordan vi praktisk implementerer CI i GitHub:

Når man jobber i et team med flere medlemmer, er det mulig å lage en YAML-fil der man definerer trinnene for en CI-prosess. 
Denne filen gir deg muligheten til å spesifisere hvordan CI-prosessen skal utføres, inkludert testing, bygging og eventuelle andre nødvendige steg.
Ved å benytte GitHub Actions, kan vi automatisere CI-prosessen. Workflowen trigges av hendelser som for eksempel en push til repositoriet. 
I tillegg kan vi beskytte hovedgrenen vår ved å kreve at CI-prosessen må være vellykket før integrasjon tillates. 
Alternativt kan man implementere en to-stegs godkjenningsprosess der en annen person må gi sin godkjenning før integrasjon tillates.


## Oppgave B 

### 1. Scrum Metodikk
    
Scrum representerer en smidig rammeverk for programvareutvikling med hovedfokus på rask produktleveranse. 
Typisk inneholder et Scrum-team ulike roller, som en produkteier, en scrum master, og utviklingsteamet.

I starten av hver iterasjon, kjent som en sprint, som kan variere fra 1 til 4 uker, planlegger teamet arbeidet basert på oppgaver fra produktbackloggen. 
Før hver sprint har teamet et planleggingsmøte der de velger oppgaver og setter prioriteringer.

Underveis i sprinten, har teamet daglige scrum-møter for å oppdatere hverandre om fremdriften, diskutere eventuelle utfordringer og be om hjelp ved behov. 
Etter hver sprint avholdes en avslutning for å evaluere oppnådd funksjonalitet og gi tilbakemeldinger for prosessforbedring.

Scrum tilbyr smidighet ved å tilpasse seg endringer i krav og prioriteringer raskt. Prosjekter praktiserer også kontinuerlig integrasjon (CI), 
hvor arbeidet som er ferdigstilt i sprinten, blir integrert og publisert, for eksempel på GitHub.

Fordelene med Scrum inkluderer daglige standup-møter for effektiv kommunikasjon og samarbeid. 
Utfordringer kan oppstå når teamet ikke er vant til Scrum, spesielt hvis de kommer fra en vannfall-modell. 
Estimering av arbeidsmengde og mangel på kommunikasjon kan også være utfordrende elementer å håndtere.

 
### 2. DevOps Metodikk
 
DevOps er en metodikk som forener prinsippene for utvikling (Dev) og drift (Ops). 
På et grunnleggende nivå handler DevOps om å skape en kultur preget av samarbeid, der kommunikasjonen flyter fritt og alle deler et felles ansvar for utviklingsprosessen.

I DevOps implementerer man automatiserte bygg- og testprosesser for å redusere feilmarginen. 
Kontinuerlig leveranse muliggjør endringer i produksjon når som helst, og med hver ny leveranse følger kontinuerlig overvåking, logging og feilsøking.

Kvaliteten på DevOps-prosessen er nøye ivaretatt gjennom automatiserte tester og kontinuerlig integrasjon, 
som sikrer tidlig oppdagelse av feil. Den automatiserte testingen og kontinuerlige leveransen øker hastigheten på programvareleveranser, 
slik at tilpasninger til endringer kan gjennomføres raskt.

DevOps styrker inkluderer raske leveranser, forbedret samarbeidsflyt, muligheten for automatisering og kontinuerlig tilbakemelding. 
Likevel, utfordringer kan oppstå, inkludert motstand fra team som ikke er vant til DevOps-metodikken, 
samt potensielle sikkerhetsrisikoer som kan følge med rask leveranse.


 
### 3. Sammenligning og Kontrast
 
Scrum og DevOps deler flere likheter. Innenfor Scrum implementeres automatiserte tester og kontinuerlig integrasjon for å oppdage feil tidlig, 
noe som resulterer i forbedret kodekvalitet og raskere tilbakemeldinger. Scrum vektlegger samarbeid og effektiv kommunikasjon, 
noe som bidrar til en bedre forståelse av kundebehov og kan føre til et forbedret sluttprodukt.

DevOps, på den annen side, fokuserer på automatisering av prosesser og kontinuerlig leveranse for å redusere manuelle inngrep, 
minimere feil og sikre konsistente utrullinger. Den gir også omfattende overvåking og logging, 
som muliggjør rask identifisering og løsning av potensielle problemer. 
Sammen utgjør disse metodologiene et kraftig verktøysett for å skape effektive og pålitelige utviklings- og driftsprosesser.

Estimering av arbeidsmengde kan være utfordrende innenfor Scrum, ofte på grunn av teamets erfaringsnivå med Scrum-prinsippene. 
Implementeringen av DevOps-kultur krever endringer, spesielt for de som ikke har tidligere erfaring med denne praksisen, 
noe som kan utfordre bedrifter med etablerte rutiner.

Når det gjelder leveranse, gir Scrum mulighet for hyppige og planlagte utgivelser gjennom sprintene. 
Dette gir rask tilbakemelding og en grad av fleksibilitet for å tilpasse seg endringer i krav, slik at ny funksjonalitet jevnlig kan leveres.
Utfordringer kan oppstå når hastigheten til utviklingsteamet varierer, og nøye planlegging av sprinter er avgjørende for å oppnå jevn leveranse.

DevOps, derimot, introduserer automatiserte bygge- og distribusjonsprosesser som muliggjør kontinuerlig leveranse. 
Dette reduserer tiden fra fullføring av kode til produksjonssetting. Likevel kan utfordringer oppstå, spesielt for store bedrifter, 
der implementeringen av DevOps kan virke kompleks og kreve betydelig tilpasning.
 
 
Hvis prosjektet har tydelig definerte krav, korte iterasjoner, og teamet er komfortabelt med å arbeide i smidige prinsipper, 
samtidig som kunden er engasjert gjennom hele utviklingsprosessen, ville jeg foretrukket å anvende Scrum eller andre smidige metoder.

Dersom kontinuerlig leveranse og hyppige kritiske utgivelser er avgjørende for prosjektet, 
og kunden ønsker optimalisert infrastruktur som kode i tillegg til automatisering av hele leveranseprosessen, 
ville jeg sannsynligvis velge DevOps. Denne tilnærmingen gir muligheten til å oppnå jevn leveranse og rask respons på endringer, 
samtidig som den legger vekt på optimalisering av infrastrukturen gjennom kodestyring.

 
## Oppgave C

Når det gjelder tilbakemeldinger, eksisterer det flere tilnærminger for å samle inn verdifulle tilbakemeldinger gjennom ulike tester. 
For eksempel kan brukertesting være en effektiv metode, der et ferdig produkt blir gitt til brukerne for å evaluere den nye funksjonaliteten. 
Ved å samle en gruppe representativ brukere og observere deres brukeropplevelse, kan vi analysere interaksjonene deres, identifisere feil og innhente direkte tilbakemelding om hva som fungerer bra og hva som kan forbedres.

En annen tilnærming er A/B-testing, hvor en gruppe bruker den nye funksjonaliteten mens den andre beholder den eksisterende. 
Dette gir oss muligheten til å sammenligne resultater mellom gruppene og evaluere effekten av den nye funksjonaliteten.

Når vi mottar tilbakemeldinger, kan vi sikre at den nye funksjonaliteten er i tråd med brukernes forventninger og behov. 
Direkte tilbakemeldinger fra brukerne bekrefter at de har prøvd ut funksjonaliteten. Gjennom tilbakemeldinger kan utviklingsteamet identifisere svakheter og områder som krever forbedring.

Kontinuerlig tilbakemelding sikrer at programvareutviklingen forblir responsiv, tilpasningsdyktig og i stand til å imøtekomme endrede brukerbehov over tid. 
Å lytte til brukernes tilbakemeldinger og integrere dem gjennom hele utviklingsprosessen er nøkkelen for å levere høykvalitetsprodukter som virkelig møter brukernes behov.