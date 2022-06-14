import Event from "../Event";

interface SorRegion {
  region: string;
  totalSize: number;
}

interface SorRegionGroup {
  regionType: string;
  regions: SorRegion[];
}

export interface SorResultType {
  resultType: string;
  regionGroups: SorRegionGroup[];
}

export interface MetaSorInfo {
  availableEvents: Event[];
  resultTypes: SorResultType[];
}
