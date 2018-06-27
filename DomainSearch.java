import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DomainSearch {

    public static void main(String args[]) {

        DomainSearch parse = new DomainSearch();

        try {
            List<Domain> domains = parse.readCSV(args[0]);
//            parse.search(domains, args);
            parse.search(domains, Arrays.copyOfRange(args, 1, args.length));
        } catch (FileNotFoundException e) {
            System.out.println("Exception: " + e.toString());
        } catch (IOException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    public String column(Domain domain, String column) {

        switch (column) {

            case "GlobalRank":
                return domain.getGlobalRank();

            case "TldRank":
                return domain.gettIdRank();

            case "Domain":
                return domain.getDomain();

            case "TLD":
                return domain.getTld();

            case "RefSubNets":
                return domain.getRefSubNets();

            case "RefIPs":
                return domain.getRefIPs();

            case "IDN_Domain":
                return domain.getIdnDomains();

            case "IDN_TLD":
                return domain.getIdnTLD();

            case "PrevGlobalRank":
                return domain.getPreGlobalRank();

            case "PrevTldRank":
                return domain.getPreTIdRank();

            case "PrevRefSubNets":
                return domain.getPreRefSubNets();

            case "PrevRefIPs":
                return domain.getPrevRefIPs();
        }

        return domain.getGlobalRank();
    }

    public void search(List<Domain> domains, String args[]) {

        int i = 0;
        String column = args[0];

        args = Arrays.copyOfRange(args, 1, args.length);

        Predicate<Domain> predicate = null;

        do {
            if (i == 0) {

                String arg = args[i];
                predicate = x -> column(x, column).equals(arg);

            } else {

                String arg = args[i];
                predicate = predicate.or((Predicate<Domain>) x -> column(x, column).equals(arg));
            }

            i++;

        } while (args.length > i);

        print(domains.stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }

    public void print(List<Domain> domains) {

        System.out.println("========================");
        for (Domain domain : domains) {
            System.out.println(domain.domain);
        }
        System.out.println("========================");
    }

    public List<Domain> readCSV(String fileName) throws FileNotFoundException, IOException {

        List<Domain> domains = new ArrayList<Domain>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine(); // Reading header, Ignoring

        while ((line = br.readLine()) != null && !line.isEmpty()) {

            String[] fields = line.split(",");

            String globalRank = fields[0];
            String tIdRank = fields[1];
            String domain = fields[2];
            String tld = fields[3];
            String refSubNets = fields[4];
            String refIPs = fields[5];
            String idnDomains = fields[6];
            String idnTLD = fields[7];
            String preGlobalRank = fields[8];
            String preTIdRank = fields[9];
            String preRefSubNets = fields[10];
            String prevRefIPs = fields[11];

            Domain domainObj = new Domain(globalRank, tIdRank, domain, tld, refSubNets, refIPs, idnDomains, idnTLD, preGlobalRank, preTIdRank, preRefSubNets, prevRefIPs);
            domains.add(domainObj);
        }

        br.close();
        return domains;
    }

    class Domain {

        private String globalRank, tIdRank, domain, tld, refSubNets, refIPs, idnDomains, idnTLD, preGlobalRank, preTIdRank, preRefSubNets, prevRefIPs;

        public Domain(String globalRank, String tIdRank, String domain, String tld, String refSubNets, String refIPs, String idnDomains, String idnTLD, String preGlobalRank, String preTIdRank, String preRefSubNets, String prevRefIPs) {
            this.globalRank = globalRank;
            this.tIdRank = tIdRank;
            this.domain = domain;
            this.tld = tld;
            this.refSubNets = refSubNets;
            this.refIPs = refIPs;
            this.idnDomains = idnDomains;
            this.idnTLD = idnTLD;
            this.preGlobalRank = preGlobalRank;
            this.preTIdRank = preTIdRank;
            this.preRefSubNets = preRefSubNets;
            this.prevRefIPs = prevRefIPs;
        }

        @Override
        public String toString() {
            return "Domain{" +
                    "globalRank='" + globalRank + '\'' +
                    ", tIdRank='" + tIdRank + '\'' +
                    ", domain='" + domain + '\'' +
                    ", tld='" + tld + '\'' +
                    ", refSubNets='" + refSubNets + '\'' +
                    ", refIPs='" + refIPs + '\'' +
                    ", idnDomains='" + idnDomains + '\'' +
                    ", idnTLD='" + idnTLD + '\'' +
                    ", preGlobalRank='" + preGlobalRank + '\'' +
                    ", preTIdRank='" + preTIdRank + '\'' +
                    ", preRefSubNets='" + preRefSubNets + '\'' +
                    ", prevRefIPs='" + prevRefIPs + '\'' +
                    '}';
        }

        public String getGlobalRank() {
            return globalRank;
        }

        public String gettIdRank() {
            return tIdRank;
        }

        public String getDomain() {
            return domain;
        }

        public String getTld() {
            return tld;
        }

        public String getRefSubNets() {
            return refSubNets;
        }

        public String getRefIPs() {
            return refIPs;
        }

        public String getIdnDomains() {
            return idnDomains;
        }

        public String getIdnTLD() {
            return idnTLD;
        }

        public String getPreGlobalRank() {
            return preGlobalRank;
        }

        public String getPreTIdRank() {
            return preTIdRank;
        }

        public String getPreRefSubNets() {
            return preRefSubNets;
        }

        public String getPrevRefIPs() {
            return prevRefIPs;
        }
    }
}