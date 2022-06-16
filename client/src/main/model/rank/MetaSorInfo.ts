import WcaEvent from "../Event";

interface SorRegion {
  region: string;
  totalSize: number;
}

interface SorRegionGroup {
  regionType: string;
  regions: SorRegion[];
}

export interface MetaSorInfo {
  resultType: string;
  regionGroups: SorRegionGroup[];
  availableEvents: WcaEvent[];
}
